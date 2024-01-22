package com.example.geeks.repository;

import com.example.geeks.domain.Detail;
import com.example.geeks.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DetailRepository extends JpaRepository<Detail, Long> {
    @Query("select d from Detail d where d.member.id = :id")
    List<Detail> findByMemberId(@Param("id") Long id);


    @Query("select d from Detail d where d.member.id not in :friend")
    List<Detail> findListNotInFriendId(@Param("friend") List<Long> friend);
}
