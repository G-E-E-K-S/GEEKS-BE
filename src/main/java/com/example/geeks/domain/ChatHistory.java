package com.example.geeks.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class ChatHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom_id")
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member sender;

    private String message;

    private int readCount = 2;

    private LocalDateTime createdAt;
    public ChatHistory(ChatRoom chatRoom, Member sender, String message, LocalDateTime createdAt) {
        this.chatRoom = chatRoom;
        this.sender = sender;
        this.message = message;
        this.createdAt = createdAt;
    }
    public static ChatHistory create(ChatRoom chatRoom, Member sender, String message, LocalDateTime createdAt) {
        ChatHistory chatHistory = new ChatHistory(chatRoom, sender, message, createdAt);
        chatRoom.getHistories().add(chatHistory);
        return chatHistory;
    }
}