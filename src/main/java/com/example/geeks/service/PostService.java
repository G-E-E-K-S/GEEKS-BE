package com.example.geeks.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.geeks.domain.Member;
import com.example.geeks.domain.Photo;
import com.example.geeks.domain.Post;
import com.example.geeks.repository.MemberRepository;
import com.example.geeks.repository.PhotoRepository;
import com.example.geeks.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    private final PhotoRepository photoRepository;

    private final MemberRepository memberRepository;

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public void uploadToS3(MultipartFile file, String fileName) throws IOException {
        ObjectMetadata metadata = new ObjectMetadata();

        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        amazonS3.putObject(bucket, fileName, file.getInputStream(), metadata);
    }

    @Transactional
    public void createPost(Long memberId, String title, String content, List<MultipartFile> files) {
        Member member = memberRepository.findById(memberId).get();

        Post post = Post.builder()
                .title(title)
                .content(content)
                .like_count(0)
                .build();
        post.setMember(member);

        postRepository.save(post);

        if(files != null) {
            int count = 1;

            for (MultipartFile file : files) {
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                String current_date = now.format(dateTimeFormatter);

                String fileName = member.getNickname() + current_date + count++;
                System.out.println("file name: " + fileName);

                //S3에 업로드 하는 기능 추가하기

                Photo photo = Photo.createPhoto(fileName, post);
                photoRepository.save(photo);
            }
        }
    }
}
