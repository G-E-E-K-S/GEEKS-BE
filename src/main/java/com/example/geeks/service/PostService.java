package com.example.geeks.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.geeks.domain.Comment;
import com.example.geeks.domain.Member;
import com.example.geeks.domain.Photo;
import com.example.geeks.domain.Post;
import com.example.geeks.repository.CommentRepository;
import com.example.geeks.repository.MemberRepository;
import com.example.geeks.repository.PhotoRepository;
import com.example.geeks.repository.PostRepository;
import com.example.geeks.requestDto.PostCommentRequestDTO;
import com.example.geeks.responseDto.PostAllDTO;
import com.example.geeks.responseDto.PostCommentResponseDTO;
import com.example.geeks.responseDto.PostDetailDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    private final PhotoRepository photoRepository;

    private final MemberRepository memberRepository;

    private final CommentRepository commentRepository;

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
    public void createPost(Long memberId, String title, String content, List<MultipartFile> files) throws IOException {
        Member member = memberRepository.findById(memberId).get();

        Post post = Post.builder()
                .title(title)
                .content(content)
                .like_count(0)
                .type(member.getType())
                .build();
        post.setMember(member);

        postRepository.save(post);

        if (files != null) {
            int count = 1;

            for (MultipartFile file : files) {
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
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

    public List<PostAllDTO> findAllPost() {
        List<Post> posts = postRepository.findAll();

        List<PostAllDTO> result = posts
                .stream()
                .map(post -> new PostAllDTO(post.getId(), post.getTitle(), post.getContent(),
                        !post.getPhotos().isEmpty() ? post.getPhotos().get(0).getPhotoName() : null,
                        post.getComments().size(), post.getCreatedDate())).toList();

        return result;
    }

    public PostDetailDTO findDetailPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Could not found id : " + postId));

        List<PostCommentResponseDTO> comments = commentRepository.findByPostId(postId);
        List<String> photoNames = photoRepository.findPhotoNamesByPostId(postId);

        return PostDetailDTO.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .comments(comments)
                .photoNames(photoNames)
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
        Member member = memberRepository.findById(memberId)
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
    }

    public List<PostCommentResponseDTO> selectComment(Long postId) {
        return commentRepository.findByPostId(postId);
    }
}
