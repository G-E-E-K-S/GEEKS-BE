package com.example.geeks.responseDto;

import com.example.geeks.Enum.SuggestionState;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SuggestionAllDTO {
    private Long suggestionId;

    private String title;

    private String content;

    private String photoName;

    private int agreeCount;

    private LocalDateTime createDate;

    private SuggestionState suggestionState;

    @Builder
    public SuggestionAllDTO(Long suggestionId, String title, String content, String photoName, LocalDateTime createDate, SuggestionState suggestionState, int agreeCount) {
        this.suggestionId = suggestionId;
        this.title = title;
        this.content = content;
        this.photoName = photoName;
        this.createDate = createDate;
        this.agreeCount = agreeCount;
        this.suggestionState = suggestionState;
    }
}
