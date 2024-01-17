package com.example.geeks.requestDto;

import com.example.geeks.domain.ChatHistory;
import com.example.geeks.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ChatRoomDTO {
    private String roomId;
    private Member user;
    private Member opponentUser;
    private List<ChatHistory> histories;

    public ChatRoomDTO(String roomId, Member user, Member opponentUser, List<ChatHistory> histories) {
        this.roomId = roomId;
        this.user = user;
        this.opponentUser = opponentUser;
        this.histories = histories;
    }
}