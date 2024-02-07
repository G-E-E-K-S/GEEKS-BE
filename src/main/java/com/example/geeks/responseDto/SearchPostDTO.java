package com.example.geeks.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class SearchPostDTO {
    private Long postId;

    private String title;

    private String content;

    private LocalDateTime createDate;
}
