package com.example.geeks.requestDto;

import com.example.geeks.Enum.DormitoryType;
import lombok.Getter;

@Getter
public class ProfileEditDTO {
    private String nickname;

    private String major;

    private DormitoryType type;

    private int studentID;

    private String introduction;


    public ProfileEditDTO(String nickname, String major, DormitoryType type, int studentID, String introduction) {
        this.nickname = nickname;
        this.major = major;
        this.type = type;
        this.studentID = studentID;
        this.introduction = introduction;
    }
}
