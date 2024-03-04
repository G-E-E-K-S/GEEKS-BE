package com.example.geeks.responseDto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class SuggestionDetailDTO {
    private String title;

    private String content;

    private int agreeCount;

    private boolean agreeState;

    private LocalDateTime createdDate;

    private List<String> photoNames;

    @Builder
    public SuggestionDetailDTO(String title, String content, int agreeCount, boolean agreeState, LocalDateTime createdDate, List<String> photoNames) {
        this.title = title;
        this.content = content;
        this.agreeCount = agreeCount;
        this.agreeState = agreeState;
        this.createdDate = createdDate;
        this.photoNames = photoNames;
    }
}
