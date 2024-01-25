package com.example.geeks.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.geeks.domain.*;
import com.example.geeks.repository.*;
import com.example.geeks.requestDto.PostCommentRequestDTO;
import com.example.geeks.requestDto.PostCreateRequestDTO;
import com.example.geeks.responseDto.PostAllDTO;
import com.example.geeks.responseDto.PostCommentResponseDTO;
import com.example.geeks.responseDto.PostCursorPageDTO;
import com.example.geeks.responseDto.PostDetailDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    private final PhotoRepository photoRepository;

    private final MemberRepository memberRepository;

    private final CommentRepository commentRepository;

    private final HeartRepository heartRepository;

    private final PostScrapRepository postScrapRepository;

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public void uploadToS3(MultipartFile file, String fileName) throws IOException {
        ObjectMetadata metadata = new ObjectMetadata();

        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        amazonS3.putObject(bucket, fileName, file.getInputStream(), metadata);
        System.out.println("사진 URL" + amazonS3.getUrl(bucket, fileName));
    }

    @Transactional
    public void createPost(Long memberId, PostCreateRequestDTO requestDTO, List<MultipartFile> files) throws IOException {
        Member member = memberRepository.findByIdFetchDetail(memberId)
                .orElseThrow(() -> new NotFoundException("Could not found id : " + memberId));

        Post post = Post.builder()
                .title(requestDTO.getTitle())
                .content(requestDTO.getContent())
                .anonymity(requestDTO.isAnonymity())
                .commentCount(0)
                .like_count(0)
                .type(member.getType())
                .build();
        post.setMember(member);

        postRepository.save(post);

        if (files != null) {
            int count = 1;

            for (MultipartFile file : files) {
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
                String current_date = now.format(dateTimeFormatter);

                String fileName = member.getNickname() + current_date + count++;
                System.out.println("file name: " + fileName);

                Photo photo = Photo.createPhoto(fileName, post);
                photoRepository.save(photo);

                //S3 Upload
                uploadToS3(file, fileName);
            }
        }
    }

    public PostCursorPageDTO cursorBasePaging(Long cursor) {
        PageRequest pageRequest =
                PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "id"));

        Page<Post> pages;

        if(cursor == 0L) {
            pages = postRepository.findPostCursorBasePagingFirst(pageRequest);
        } else {
            pages = postRepository.findPostCursorBasePaging(cursor, pageRequest);
        }

        List<PostAllDTO> postAllDTOS = pages.map(post ->
                new PostAllDTO(
                        post.getId(), post.getTitle(), post.getContent(),
                        post.isAnonymity() ? null : post.getMember().getNickname(),
                        !post.getPhotos().isEmpty() ? post.getPhotos().get(0).getPhotoName() : null,
                        post.getCommentCount(), post.isAnonymity(), post.getCreatedDate())).stream().toList();


        return new PostCursorPageDTO(postAllDTOS.get(postAllDTOS.size() - 1).getPostId(), pages.hasNext(), postAllDTOS);
    }

    public PostDetailDTO findDetailPost(Long userId, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Could not found id : " + postId));

        List<PostCommentResponseDTO> comments = commentRepository.findByPostId(postId);
        List<String> photoNames = photoRepository.findPhotoNamesByPostId(postId);

        Optional<Heart> heart = heartRepository.findByMemberIdAndPostId(userId, postId);
        Optional<PostScrap> postScrap = postScrapRepository.findByMemberIdAndPostId(userId, postId);

        return PostDetailDTO.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .comments(comments)
                .commentCount(post.getCommentCount())
                .photoNames(photoNames)
                .createdDate(post.getCreatedDate())
                .writer(post.isAnonymity() ? null : post.getMember().getNickname())
                .writerState(post.getMember().getId().equals(userId))
                .scrapState(postScrap.isPresent())
                .heartState(heart.isPresent())
                .build();
    }

    @Transactional
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Could not found id : " + postId));

        List<Photo> photos = post.getPhotos();

        for (Photo photo : photos) {
            amazonS3.deleteObject(bucket, photo.getPhotoName());
        }

        postRepository.delete(post);
    }

    @Transactional
    public void createComment(Long memberId, PostCommentRequestDTO requestDTO) {
        Member member = memberRepository.findByIdFetchDetail(memberId)
                .orElseThrow(() -> new NotFoundException("Could not found member id : " + memberId));

        Post post = postRepository.findById(requestDTO.getPostId())
                .orElseThrow(() -> new NotFoundException("Could not found post id : " + requestDTO.getPostId()));

        Comment comment = new Comment(requestDTO.getContent(), false);
        comment.setMember(member);
        comment.setPost(post);

        Comment parentComment;

        if (requestDTO.getParentId() != null) {
            parentComment = commentRepository.findById(requestDTO.getParentId())
                    .orElseThrow(() -> new NotFoundException("Could not found parent id : " + requestDTO.getParentId()));
            comment.setParent(parentComment);
        }

        commentRepository.save(comment);

        if(comment.getId() != null) {
            post.increaseCommentCount(post.getCommentCount() + 1);
        }
    }

    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Could not found id : " + commentId));

        comment.setDeleted(true);
    }

    public List<PostCommentResponseDTO> selectComment(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    @Transactional
    public void insertHeart(Long userId, Long postId) throws Exception {
        Member member = memberRepository.findByIdFetchDetail(userId)
                .orElseThrow(() -> new NotFoundException("Could not found id : " + userId));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Could not found id : " + postId));

        if(heartRepository.findByMemberAndPost(member, post).isPresent()) {
            throw new Exception();
        }

        Heart heart = new Heart();
        heart.setPost(post);
        heart.setMember(member);

        heartRepository.save(heart);
        postRepository.increaseHeart(postId);
    }

    @Transactional
    public void deleteHeart(Long userId, Long postId) {
        Member member = memberRepository.findByIdFetchDetail(userId)
                .orElseThrow(() -> new NotFoundException("Could not found id : " + userId));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Could not found id : " + postId));

        Heart heart = heartRepository.findByMemberAndPost(member, post)
                .orElseThrow(() -> new NotFoundException("Could not found heart"));

        heartRepository.delete(heart);
        postRepository.decreaseHeart(postId);
    }
    
    @Transactional
    public void insertScrap(Long userId, Long postId) throws Exception {
        Member member = memberRepository.findByIdFetchDetail(userId)
                .orElseThrow(() -> new NotFoundException("Could not found id : " + userId));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Could not found id : " + postId));

        if(postScrapRepository.findByMemberAndPost(member, post).isPresent()) {
            throw new Exception();
        }

        PostScrap postScrap = new PostScrap();
        postScrap.setPost(post);
        postScrap.setMember(member);

        postScrapRepository.save(postScrap);
    }

    @Transactional
    public void deleteScrap(Long userId, Long postId) {
        Member member = memberRepository.findByIdFetchDetail(userId)
                .orElseThrow(() -> new NotFoundException("Could not found id : " + userId));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Could not found id : " + postId));

        PostScrap postScrap = postScrapRepository.findByMemberAndPost(member, post)
                .orElseThrow(() -> new NotFoundException("Could not found scrap"));

        postScrapRepository.delete(postScrap);
    }
}
