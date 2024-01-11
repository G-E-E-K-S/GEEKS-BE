package com.example.geeks.requestDto;

import com.example.geeks.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@Component
public class ChatRoomDTO {
    private Long id;
    private String roomId;
    private Member user;
    private Member opponentUser;
    //private List<ChatHistory> histories;

    public int insertChatting(ChatMessage chatMessage){
        return 1;
    }
}