package com.example.geeks.repository;

import com.example.geeks.domain.ChatRoom;
import com.example.geeks.domain.Detail;
import com.example.geeks.domain.Member;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findByUserAndOpponentUser(Member user, Member opponentUser);

    ChatRoom findById(String id);

//    @Query("select cr from ChatRoom cr where cr.roomId = : roomId")
    ChatRoom findByRoomId(@Param("roomId") String roomId);

    @Query("SELECT cr FROM ChatRoom cr " +
            "WHERE cr.user.id = :userId OR cr.opponentUser.id = :userId")
    List<ChatRoom> findByUserOrOpponentUser(@Param("userId") Long userId);

    @Modifying
    @Query("delete from ChatRoom cr " +
            "where cr.user.id = :id or cr.opponentUser = :id")
    void deleteByUserOrOpponentuser(@Param("id") Long id);

    void deleteByRoomId(@Param("id") String id);
}
