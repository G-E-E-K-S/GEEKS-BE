package com.example.geeks.repository;

import com.example.geeks.domain.ChatRoom;
import com.example.geeks.domain.Member;
import com.example.geeks.domain.RoomMate;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomMateRepository extends JpaRepository<RoomMate, Long> {
    @Query("select r from RoomMate r where r.sent = :sent")
    List<RoomMate> findSentByMember(@Param("sent") Member sent);


    @Query("select r from RoomMate r where r.received = :received")
    List<RoomMate> findRecivedByMember(@Param("received") Member received);

    void deleteBySentAndReceived(Member sent, Member received);
}
