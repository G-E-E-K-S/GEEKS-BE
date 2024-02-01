package com.example.geeks.repository;

import com.example.geeks.domain.AcceptRoomMate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AcceptRoomMateRepository extends JpaRepository<AcceptRoomMate, Long> {

    @Query("select ac from AcceptRoomMate ac " +
            "where ac.accept.id = :userId or ac.sender.id = :userId")
    Optional<AcceptRoomMate> findAcceptRoomMateOptional(@Param("userId") Long userId);
}
