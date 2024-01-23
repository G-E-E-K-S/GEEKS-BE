package com.example.geeks.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PointAndMemberDTO {

    private Long userId;

    private String nickname;

    private String major;

    private String introduction;

    private String photoName;

    private int studentID;

    private int point;

}
