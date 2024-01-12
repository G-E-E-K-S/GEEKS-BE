package com.example.geeks.requestDto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCreateRequestDTO {

    private String title;

    private String content;

    private List<MultipartFile> photos;
}
