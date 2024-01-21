package com.example.geeks.responseDto;

import com.example.geeks.requestDto.ChatMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class SendChatMessage {
    private Long chatId;
    private String roomId;
    private String sender;
    private String message;
    private LocalDateTime createdAt;

    public SendChatMessage(Long chatId, String roomUUID, String senderId, String message, LocalDateTime createdAt) {
        this.chatId = chatId;
        this.roomId = roomUUID;
        this.sender = senderId;
        this.message = message;
        this.createdAt = createdAt;
    }

    public static SendChatMessage of(Long chatId, ChatMessage message) {
        return new SendChatMessage(
                chatId,
                message.getRoomid(),
                message.getUser(),
                message.getContent(),
                message.getCreateAt()
        );
    }
}
