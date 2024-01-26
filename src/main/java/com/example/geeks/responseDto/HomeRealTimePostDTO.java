package com.example.geeks.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class HomeRealTimePostDTO {
    private String title;

    private String content;

    private int likeCount;

    private int commentCount;

    private LocalDateTime createdDate;
}
