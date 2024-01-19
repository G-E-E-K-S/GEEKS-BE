package com.example.geeks.responseDto;

import com.example.geeks.domain.ChatHistory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ChatHistoryResponse {
    private String sender;
    private String message;
    private int readCount;
    private LocalDateTime createdAt;

    public ChatHistoryResponse(String sender, int readCount, String message, LocalDateTime createdAt) {
        this.sender = sender;
        this.message = message;
        this.readCount = readCount;
        this.createdAt = createdAt;
    }
    public static ChatHistoryResponse of(ChatHistory chatHistory) {
        return new ChatHistoryResponse(
                chatHistory.getSender().getNickname(),
                chatHistory.getReadCount(),
                chatHistory.getMessage(),
                chatHistory.getCreatedAt()
        );
    }
}
