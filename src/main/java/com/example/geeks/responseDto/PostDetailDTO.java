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

    private int likeCount;

    private int commentCount;

    private boolean writerState;

    private boolean heartState;

    private boolean scrapState;

    private LocalDateTime createdDate;

    private List<String> photoNames;

    private List<PostCommentResponseDTO> comments;

    @Builder
    public PostDetailDTO(String title, String content, String writer, int likeCount, int commentCount, boolean writerState, boolean heartState, boolean scrapState, LocalDateTime createdDate, List<String> photoNames, List<PostCommentResponseDTO> comments) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.writerState = writerState;
        this.heartState = heartState;
        this.scrapState = scrapState;
        this.createdDate = createdDate;
        this.photoNames = photoNames;
        this.comments = comments;
    }
}
