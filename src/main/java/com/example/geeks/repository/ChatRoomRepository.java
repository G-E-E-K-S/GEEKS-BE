package com.example.geeks.repository;

import com.example.geeks.domain.ChatRoom;
import com.example.geeks.domain.Detail;
import com.example.geeks.domain.Member;
import com.example.geeks.requestDto.ChatRoomDTO;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findByUserAndOpponentUser(Member user, Member opponentUser);

    ChatRoom findById(String id);

    @Query("SELECT cr FROM ChatRoom cr WHERE cr.user.id = :user OR cr.opponentUser.id = :user")
    List<ChatRoom> findByUserOrOpponentUser(@Param("user") Long user);
}
