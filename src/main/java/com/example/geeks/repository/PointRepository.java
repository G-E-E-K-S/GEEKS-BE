package com.example.geeks.repository;

import com.example.geeks.domain.Member;
import com.example.geeks.domain.Point;
import com.example.geeks.responseDto.PointAndMemberDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PointRepository extends JpaRepository<Point, Long> {

    @Query("select p from Point p " +
            "left join fetch p.friend f " +
            "left join fetch f.detail d " +
            "where p.member.id = :memberId ")
    List<Point> findByMemberIdFetch(@Param("memberId")Long memberId);

    @Query("select p " +
            "from Point p " +
            "left join fetch p.friend f " +
            "where p.member.id = :userId")
    List<Point> findFetchMember(@Param("userId") Long userId);

    Point findByMemberAndFriend(@Param("member") Member member,
                                @Param("friend") Member friend);
}
