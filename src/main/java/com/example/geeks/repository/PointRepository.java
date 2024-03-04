package com.example.geeks.repository;

import com.example.geeks.domain.Member;
import com.example.geeks.domain.Point;
import com.example.geeks.responseDto.PointAndMemberDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PointRepository extends JpaRepository<Point, Long> {

    @Query("select p from Point p " +
            "left join fetch p.friend f " +
            "left join fetch f.detail d " +
            "where p.member.id = :memberId " +
            "order by p.point desc ")
    List<Point> findByMemberIdFetch(@Param("memberId")Long memberId);

    @Query("select p from Point p " +
            "left join fetch p.friend f " +
            "left join fetch f.detail d " +
            "where p.member.id = :memberId and f.open = true " +
            "order by p.point desc ")
    List<Point> findByMemberIdFetchToHome(@Param("memberId")Long memberId,
                                          Pageable pageable);

    @Query("select p " +
            "from Point p " +
            "left join fetch p.friend f " +
            "left join fetch f.detail d " +
            "where p.member.id = :userId and f.open = true " +
            "order by p.point desc ")
    List<Point> findFetchMember(@Param("userId") Long userId);

    @Query("select p from Point p " +
            "where p.member.id = :memberId and " +
            "p.friend.id = :friendId")
    Optional<Point> findByMemberAndFriend(@Param("memberId") Long memberId,
                                         @Param("friendId") Long friendId);

    @Query("select p from Point p " +
            "left join fetch p.friend f " +
            "left join fetch f.detail d " +
            "where f.id in :friend and p.member.id = :userId")
    List<Point> findByFriendIdInListFetch(@Param("friend") List<Long> friend,
                                          @Param("userId") Long userId);

    @Query("select p.point from Point p " +
            "where p.member.id = :myId and p.friend.id = :friendId")
    int findPointByMemberIdAndFriendId(@Param("myId") Long myId,
                                       @Param("friendId") Long friendId);

    @Modifying
    @Query("delete from Point p " +
            "where p.member.id = :userId or p.friend.id = :userId")
    void deletePointWhenChangeType(@Param("userId") Long userId);

    @Modifying
    @Query("delete from Point p " +
            "where p.friend.id = :userId " +
            "or p.member.id = :userId")
    void deletePointWhenMemberWithDraw(@Param("userId") Long userId);

}
