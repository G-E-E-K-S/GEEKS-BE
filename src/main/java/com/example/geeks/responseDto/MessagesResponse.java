package com.example.geeks.responseDto;

import com.example.geeks.domain.ChatRoom;
import com.example.geeks.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class MessagesResponse {
    private SimpleUserResponse you;
    private List<ChatHistoryResponse> chatHistories;


    public MessagesResponse(SimpleUserResponse you, List<ChatHistoryResponse> chatHistories) {
        this.you = you;
        this.chatHistories = chatHistories;
    }

    public static MessagesResponse of(Member you, ChatRoom chatRoom) {
        return new MessagesResponse(
                SimpleUserResponse.of(you),
                chatRoom.getHistories().stream()
                        .map(ChatHistoryResponse::of)
                        .collect(Collectors.toList())
        );
    }
}