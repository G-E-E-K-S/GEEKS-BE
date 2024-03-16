package com.example.geeks.responseDto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class MeetingResponseDTO {
    private String roomId;
    private String name;

    public MeetingResponseDTO(String roomId, String name){
        this.roomId = roomId;
        this.name = name;
    }
}
