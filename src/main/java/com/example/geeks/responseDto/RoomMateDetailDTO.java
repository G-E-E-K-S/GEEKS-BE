package com.example.geeks.responseDto;

import com.example.geeks.Enum.*;
import lombok.*;

@Getter
public class RoomMateDetailDTO {
    private String nickname;

    private String major;

    private String introduction;

    private String photoName;

    private int studentID;

    private boolean smoking;

    private boolean habit;

    private Ear ear;

    private Time sleep;

    private Time wakeup;

    private Out out;

    private Cleaning cleaning;

    private Tendency tendency;

    @Builder
    public RoomMateDetailDTO(String nickname, String major, String introduction, String photoName,
                             int studentID, boolean smoking, boolean habit, Ear ear, Time sleep,
                             Time wakeup, Out out, Cleaning cleaning, Tendency tendency) {
        this.nickname = nickname;
        this.major = major;
        this.introduction = introduction;
        this.photoName = photoName;
        this.studentID = studentID;
        this.smoking = smoking;
        this.habit = habit;
        this.ear = ear;
        this.sleep = sleep;
        this.wakeup = wakeup;
        this.out = out;
        this.cleaning = cleaning;
        this.tendency = tendency;
    }
}
