package com.example.geeks.responseDto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class PostAllDTO {

    private Long postId;

    private String title;

    private String content;

    private String photoName;

    private int commentCount;

    private LocalDateTime createdDate;

    public PostAllDTO(Long postId, String title, String content, String photoName, int commentCount, LocalDateTime createdDate) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.photoName = photoName;
        this.commentCount = commentCount;
        this.createdDate = createdDate;
    }
}
