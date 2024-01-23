package com.example.geeks.repository;

import com.example.geeks.domain.Member;
import com.example.geeks.domain.Point;
import com.example.geeks.domain.RoomMate;
import com.example.geeks.domain.SaveRoomMate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SaveRoomMateRepository extends JpaRepository<SaveRoomMate, Long> {
    @Query("select s from SaveRoomMate s " +
            "left join fetch s.me m " +
            "left join fetch s.you y " +
            "where m.id = :memberId ")
    List<SaveRoomMate> findAllByIdFetch(@Param("memberId")Long memberId);
}
