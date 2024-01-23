package com.example.geeks.responseDto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;

import java.time.LocalDateTime;

@Getter
public class RoomMateDTO {
    private String nickname;

    private String major;

    private String introduction;

    private String photoName;

    private int studentID;

    private LocalDateTime createdDate;

    @Builder
    public RoomMateDTO(String nickname, String major, String introduction, String photoName, int studentID, LocalDateTime createdDate) {
        this.nickname = nickname;
        this.major = major;
        this.introduction = introduction;
        this.photoName = photoName;
        this.studentID = studentID;
        this.createdDate = createdDate;
    }
}
