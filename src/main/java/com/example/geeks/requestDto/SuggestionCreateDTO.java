package com.example.geeks.requestDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SuggestionCreateDTO {
    private String title;

    private String content;
}
