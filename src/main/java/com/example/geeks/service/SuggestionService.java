package com.example.geeks.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.geeks.Enum.SuggestionState;
import com.example.geeks.domain.*;
import com.example.geeks.repository.MemberRepository;
import com.example.geeks.repository.SuggestionPhotoRepository;
import com.example.geeks.repository.SuggestionRepository;
import com.example.geeks.requestDto.PostCreateRequestDTO;
import com.example.geeks.requestDto.SuggestionCreateDTO;
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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SuggestionService {

    private final MemberRepository memberRepository;

    private final SuggestionRepository suggestionRepository;

    private final SuggestionPhotoRepository suggestionPhotoRepository;

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public List<String> uploadToS3(List<MultipartFile> files, String nickname) {
        List<String> fileNames = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()); // 최적의 스레드 풀 크기

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String current_date = now.format(dateTimeFormatter);

        try {
            AtomicInteger count = new AtomicInteger(1); // 고유한 파일 번호를 생성하기 위한 AtomicInteger

            CompletableFuture<?>[] futures = files.stream()
                    .map(file -> CompletableFuture.runAsync(() -> {
                        try {
                            String fileName = nickname + current_date + count.getAndIncrement();

                            ObjectMetadata metadata = new ObjectMetadata();

                            metadata.setContentLength(file.getSize());
                            metadata.setContentType(file.getContentType());

                            amazonS3.putObject(bucket, fileName, file.getInputStream(), metadata);
                            System.out.println("사진 URL" + amazonS3.getUrl(bucket, fileName));
                            fileNames.add(fileName);
                        } catch (IOException e) {
                            // 에러 처리
                        }
                    }, executor))
                    .toArray(CompletableFuture[]::new);

            CompletableFuture.allOf(futures).join(); // 모든 작업이 완료될 때까지 대기
        } finally {
            executor.shutdown(); // ExecutorService 종료
        }

        return fileNames;
    }

    @Transactional
    public void createPost(Long memberId, SuggestionCreateDTO dto, List<MultipartFile> files) throws IOException {
        Member member = memberRepository.findByIdFetchDetail(memberId)
                .orElseThrow(() -> new NotFoundException("Could not found id : " + memberId));

        Suggestion suggestion = Suggestion.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .agree_count(0)
                .suggestionState(SuggestionState.NONE)
                .build();
        suggestion.setMember(member);

        suggestionRepository.save(suggestion);

        if (files != null) {
            List<String> fileNames = uploadToS3(files, member.getNickname());
            suggestion.setPhotoName(fileNames.get(0));

            for (String fileName : fileNames) {
                SuggestionPhoto photo = SuggestionPhoto.createPhoto(fileName, suggestion);
                suggestionPhotoRepository.save(photo);
            }
        }
    }
}