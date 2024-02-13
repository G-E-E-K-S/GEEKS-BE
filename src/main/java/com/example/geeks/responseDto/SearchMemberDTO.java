package com.example.geeks.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SearchMemberDTO {
    private Long userId;

    private String nickname;

    private String major;

    private String photoName;
}
