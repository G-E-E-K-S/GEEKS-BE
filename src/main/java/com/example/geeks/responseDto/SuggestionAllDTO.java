package com.example.geeks.responseDto;

import com.example.geeks.Enum.DormitoryType;
import com.example.geeks.Enum.Gender;
import com.example.geeks.Enum.SuggestionState;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SuggestionAllDTO {
    private Long postId;

    private String title;

    private String content;

    private String photoName;

    private int agreeCount;

    private LocalDateTime createDate;

    private SuggestionState suggestionState;

    private DormitoryType type;

    private Gender gender;

    @Builder
    public SuggestionAllDTO(Long suggestionId, String title, String content, String photoName, LocalDateTime createDate, SuggestionState suggestionState, int agreeCount, DormitoryType type, Gender gender) {
        this.postId = suggestionId;
        this.type = type;
        this.title = title;
        this.gender = gender;
        this.content = content;
        this.photoName = photoName;
        this.createDate = createDate;
        this.agreeCount = agreeCount;
        this.suggestionState = suggestionState;
    }
}
