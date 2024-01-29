package com.example.geeks.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class CommentHistoryDTO {
    private Long postId;

    private String title;

    private String content;

    private int commentCount;

    private int likeCount;

    private LocalDateTime createdDate;
}
