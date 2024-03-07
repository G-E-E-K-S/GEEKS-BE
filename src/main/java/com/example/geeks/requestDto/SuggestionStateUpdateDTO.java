package com.example.geeks.requestDto;

import com.example.geeks.Enum.SuggestionState;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SuggestionStateUpdateDTO {
    private Long suggestionId;

    private SuggestionState suggestionState;
}
