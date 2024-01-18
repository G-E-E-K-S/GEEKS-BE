package com.example.geeks.responseDto;

import lombok.Getter;

import java.util.List;

@Getter
public class ChatRoomDetailDTO {
    private String roomId;

    private String user;

    private String opponentUser;

    private String major;

    private int studentID;

    private List<ChatHistoryResponse> histories;

    public ChatRoomDetailDTO(String roomId, String user, String opponentUser, String major, int studentID, List<ChatHistoryResponse> histories) {
        this.roomId = roomId;
        this.user = user;
        this.opponentUser = opponentUser;
        this.major = major;
        this.studentID = studentID;
        this.histories = histories;
    }
}
