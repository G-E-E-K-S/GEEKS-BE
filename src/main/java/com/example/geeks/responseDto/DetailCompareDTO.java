package com.example.geeks.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class DetailCompareDTO {
    private String nickname;

    private String major;

    private String photoName;

    private String introduction;

    private int studentID;

    List<DetailDTO> details;
}
