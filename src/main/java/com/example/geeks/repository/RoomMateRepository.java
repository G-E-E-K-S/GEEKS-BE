package com.example.geeks.repository;

import com.example.geeks.domain.ChatRoom;
import com.example.geeks.domain.Member;
import com.example.geeks.domain.RoomMate;
import com.example.geeks.domain.SaveRoomMate;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomMateRepository extends JpaRepository<RoomMate, Long> {
    @Query("select r from RoomMate r " +
            "left join fetch r.sent m " +
            "left join  fetch r.received s " +
            "where m.id = :memberId ")
    List<RoomMate> findSentByMemberId(@Param("memberId") Long memberId);

    @Query("select r from RoomMate r " +
            "left join fetch r.received m " +
            "left join  fetch r.sent s " +
            "where m.id = :memberId ")
    List<RoomMate> findRecivedById(@Param("memberId") Long memberId);


    RoomMate findBySentAndReceived(Member sent, Member recived);

    void deleteBySentAndReceived(Member sent, Member received);
}
