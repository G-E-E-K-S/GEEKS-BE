package com.example.geeks.responseDto;

import com.example.geeks.Enum.*;
import lombok.Builder;
import lombok.Getter;

@Getter
public class DetailResponseDTO {

    private Long detailId;
    private boolean smoking;

    private boolean habit;

    private Ear ear;

    private Time sleep;

    private Time wakeup;

    private Out out;

    private Cleaning cleaning;

    private Tendency tendency;

    private boolean exist;

    public DetailResponseDTO(boolean exist) {
        this.exist = exist;
    }

    @Builder
    public DetailResponseDTO(Long detailId, boolean smoking, boolean habit, Ear ear, Time sleep, Time wakeup, Out out, Cleaning cleaning, Tendency tendency, boolean exist) {
        this.detailId = detailId;
        this.smoking = smoking;
        this.habit = habit;
        this.ear = ear;
        this.sleep = sleep;
        this.wakeup = wakeup;
        this.out = out;
        this.cleaning = cleaning;
        this.tendency = tendency;
        this.exist = exist;
    }
}
