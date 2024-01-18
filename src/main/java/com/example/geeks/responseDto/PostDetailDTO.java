package com.example.geeks.responseDto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostDetailDTO {
    private String title;

    private String content;

    private String writer;

    private int commentCount;

    private LocalDateTime createdDate;

    private List<String> photoNames;

    private List<PostCommentResponseDTO> comments;

    @Builder
    public PostDetailDTO(String title, String content, String writer, int commentCount, LocalDateTime createdDate, List<String> photoNames, List<PostCommentResponseDTO> comments) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.commentCount = commentCount;
        this.createdDate = createdDate;
        this.photoNames = photoNames;
        this.comments = comments;
    }
}
