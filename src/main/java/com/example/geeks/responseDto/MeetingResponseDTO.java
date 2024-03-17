package com.example.geeks.responseDto;


import com.example.geeks.domain.MeetingHistory;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import lombok.Getter;
import lombok.Setter;


import java.util.UUID;

@Getter
@Setter
public class MeetingResponseDTO {
    private String roomId;
    private String name;

    private List<ChatHistoryResponse> histories;
    public MeetingResponseDTO(String roomId, String name, List<ChatHistoryResponse> histories){
        this.roomId = roomId;
        this.name = name;
        this.histories = histories;


    public MeetingResponseDTO(String roomId, String name){
        this.roomId = roomId;

    }
}
