package com.example.geeks.repository;

import com.example.geeks.domain.ChatHistory;
import com.example.geeks.domain.ChatRoom;
import com.example.geeks.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatHistoryRepository extends JpaRepository<ChatHistory, Long> {
    Long save(ChatHistory chatHistory);
}
