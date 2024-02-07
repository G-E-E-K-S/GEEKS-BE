package com.example.geeks.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class SearchPostCursorDTO {
    private Long cursor;

    private boolean hasNextPage;

    private List<SearchPostDTO> posts;
}
