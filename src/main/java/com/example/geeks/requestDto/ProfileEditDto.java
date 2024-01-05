package com.example.geeks.requestDto;

import lombok.Getter;

@Getter
public class ProfileEditDto {
    private String nickname;

    private String major;

    private int studentID;

    private String introduction;

    public ProfileEditDto(String nickname, String major, int studentID, String introduction) {
        this.nickname = nickname;
        this.major = major;
        this.studentID = studentID;
        this.introduction = introduction;
    }
}
