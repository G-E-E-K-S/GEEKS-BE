package com.example.geeks.responseDto;

import com.example.geeks.Enum.DormitoryType;
import com.example.geeks.Enum.Gender;
import com.example.geeks.Enum.SuggestionState;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class SuggestionDetailDTO {

    private Long suggestionId;

    private String title;

    private String content;

    private int agreeCount;

    private boolean agreeState;

    private SuggestionState suggestionState;

    private DormitoryType type;

    private Gender gender;

    private LocalDateTime createdDate;

    private List<String> photoNames;

    @Builder
    public SuggestionDetailDTO(Long suggestionId, String title, String content, int agreeCount, boolean agreeState, LocalDateTime createdDate, List<String> photoNames, SuggestionState suggestionState, DormitoryType type, Gender gender) {
        this.suggestionId = suggestionId;
        this.type = type;
        this.title = title;
        this.gender = gender;
        this.content = content;
        this.agreeCount = agreeCount;
        this.agreeState = agreeState;
        this.createdDate = createdDate;
        this.photoNames = photoNames;
        this.suggestionState = suggestionState;
    }
}
