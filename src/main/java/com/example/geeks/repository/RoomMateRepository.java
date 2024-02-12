package com.example.geeks.repository;

import com.example.geeks.domain.ChatRoom;
import com.example.geeks.domain.Member;
import com.example.geeks.domain.RoomMate;
import com.example.geeks.domain.SaveRoomMate;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

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

    RoomMate findBySentAndReceived(Member sent, Member received);

    @Query("select rm from RoomMate rm " +
            "where rm.sent.id = :myId and rm.received.id = :opponentId")
    Optional<RoomMate> findRoomMateState(@Param("myId") Long myId,
                                         @Param("opponentId") Long opponentId);

    List<RoomMate> findRoomMateByReceivedId(@Param("id") Long id);

    @Modifying
    @Query("delete from RoomMate rm where rm.sent.id = :userId")
    void deleteSent(@Param("userId") Long userId);

    @Modifying
    @Query("delete from RoomMate rm " +
            "where rm.received.id = :acceptId " +
            "or rm.sent.id = :acceptId " +
            "or rm.received.id = :senderId " +
            "or rm.sent.id = :senderId")
    void deleteAllRoomMate(@Param("acceptId") Long acceptId,
                        @Param("senderId") Long senderId);

    @Modifying
    @Query("delete from RoomMate rm " +
            "where rm.received.id = :myId and rm.sent.id = :senderId")
    void deleteRoomMate(@Param("myId") Long myId,
                        @Param("senderId") Long senderId);

    @Modifying
    @Query("delete from RoomMate rm " +
            "where rm.received.id = :myId or rm.sent.id = :myId")
    void deleteRoomMateMyidOrSenderid(@Param("myId") Long myId);

    void deleteBySentAndReceived(Member sent, Member received);
}
