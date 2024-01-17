package com.example.geeks.responseDto;

import com.example.geeks.domain.Comment;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class PostDetailDTO {
    private String title;

    private String content;

    private List<String> photoNames;

    private List<PostCommentResponseDTO> comments;

    @Builder
    public PostDetailDTO(String title, String content, List<String> photoNames, List<PostCommentResponseDTO> comments) {
        this.title = title;
        this.content = content;
        this.photoNames = photoNames;
        this.comments = comments;
    }
}
