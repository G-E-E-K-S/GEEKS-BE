package com.example.geeks.responseDto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MyPageDTO {
    private String nickname;

    private String major;

    private String introduction;

    private String photoName;

    private int studentID;

    private boolean exist;

    @Builder
    public MyPageDTO(String nickname, String major, String introduction, String photoName, int studentID, boolean exist) {
        this.nickname = nickname;
        this.major = major;
        this.introduction = introduction;
        this.photoName = photoName;
        this.studentID = studentID;
        this.exist = exist;
    }
}
