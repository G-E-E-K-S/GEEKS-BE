package com.example.geeks.responseDto;

import com.example.geeks.domain.ChatHistory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ChatHistoryResponse {
    private String sender;
    private String message;
    private LocalDateTime createdAt;

    public ChatHistoryResponse(String sender, String message, LocalDateTime createdAt) {
        this.sender = sender;
        this.message = message;
        this.createdAt = createdAt;
    }
    public static ChatHistoryResponse of(ChatHistory chatHistory) {
        return new ChatHistoryResponse(
                chatHistory.getSender().getNickname(),
                chatHistory.getMessage(),
                chatHistory.getCreatedAt()
        );
    }
}
