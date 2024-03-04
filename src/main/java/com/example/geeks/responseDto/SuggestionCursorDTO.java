package com.example.geeks.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SuggestionCursorDTO {
    private Long cursor;

    private boolean hasNextPage;

    private List<SuggestionAllDTO> suggestions;
}
