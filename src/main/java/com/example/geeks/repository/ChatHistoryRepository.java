package com.example.geeks.repository;

import com.example.geeks.domain.ChatHistory;
import com.example.geeks.domain.ChatRoom;
import com.example.geeks.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ChatHistoryRepository extends JpaRepository<ChatHistory, Long> {
    @Modifying(clearAutomatically = true)
    @Query("update ChatHistory ch set ch.readCount = ch.readCount - 1 " +
            "where ch.readCount >= 1 and ch.sender.id != :id")
    int bulkReadCount(@Param("id") Long id);

    @Modifying
    @Query("delete from ChatHistory ch " +
            "where ch.sender.id = :id ")
    void deleteBySender(@Param("id") Long id);
}
