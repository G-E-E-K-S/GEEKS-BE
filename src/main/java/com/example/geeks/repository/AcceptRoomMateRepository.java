package com.example.geeks.repository;

import com.example.geeks.domain.AcceptRoomMate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.Optional;

public interface AcceptRoomMateRepository extends JpaRepository<AcceptRoomMate, Long> {

    @Query("select ac from AcceptRoomMate ac " +
            "where ac.accept.id = :userId or ac.sender.id = :userId")
    Optional<AcceptRoomMate> findAcceptRoomMateOptional(@Param("userId") Long userId);

    @Query("select ac from AcceptRoomMate ac " +
            "where (ac.accept.id = :myId and ac.sender.id = :opponentId) " +
            "or (ac.sender.id = :myId and ac.accept.id = :opponentId)")
    Optional<AcceptRoomMate> findAcceptRoomMate(@Param("myId") Long myId,
                                                @Param("opponentId") Long opponentId);

    @Query("select ac from AcceptRoomMate ac " +
            "where ac.accept.id = :myId " +
            "or ac.sender.id = :myId")
    Optional<AcceptRoomMate> findAcceptRoomMateState(@Param("myId") Long myId);

    @Modifying
    @Query("delete from AcceptRoomMate ac " +
            "where ac.sender.id = :userId or ac.accept.id = :userId")
    void deleteAcceptRoomMateForWithdraw(@Param("userId") Long userId);
}
