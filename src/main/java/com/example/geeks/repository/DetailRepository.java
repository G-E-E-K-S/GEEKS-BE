package com.example.geeks.repository;

import com.example.geeks.Enum.DormitoryType;
import com.example.geeks.Enum.Gender;
import com.example.geeks.domain.Detail;
import com.example.geeks.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DetailRepository extends JpaRepository<Detail, Long> {
    @Query("select d from Detail d where d.member.id = :id")
    List<Detail> findByMemberId(@Param("id") Long id);

    @Query("select d from Detail d " +
            "where d.member.id = :id")
    Optional<Detail> findDetailByMemberId(@Param("id") Long id);


    @Query("select d from Detail d " +
            "left join fetch d.member m " +
            "where m.id not in :friend " +
            "and m.gender = :gender " +
            "and m.type = :type")
    List<Detail> findListNotInFriendId(@Param("friend") List<Long> friend,
                                       @Param("gender") Gender gender,
                                       @Param("type")DormitoryType type);

}
