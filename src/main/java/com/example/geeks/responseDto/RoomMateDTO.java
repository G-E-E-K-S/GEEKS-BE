package com.example.geeks.responseDto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;

@Getter
public class RoomMateDTO {
    private String nickname;

    private String major;

    private String introduction;

    private String photoName;

    private int studentID;

    @Builder
    public RoomMateDTO(String nickname, String major, String introduction, String photoName, int studentID) {
        this.nickname = nickname;
        this.major = major;
        this.introduction = introduction;
        this.photoName = photoName;
        this.studentID = studentID;
    }
}
