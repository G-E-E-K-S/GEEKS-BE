package com.example.geeks.responseDto;

import com.example.geeks.domain.ChatHistory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ChatHistoryResponse {
    private Long senderId;
    private String message;
    private LocalDateTime createdAt;

    public ChatHistoryResponse(Long senderId, String message, LocalDateTime createdAt) {
        this.senderId = senderId;
        this.message = message;
        this.createdAt = createdAt;
    }


    public static ChatHistoryResponse of(ChatHistory chatHistory) {
        return new ChatHistoryResponse(
                chatHistory.getSender().getId(),
                chatHistory.getMessage(),
                chatHistory.getCreatedAt()
        );
    }
}
