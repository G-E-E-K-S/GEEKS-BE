package com.example.geeks.repository;

import com.example.geeks.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Modifying(clearAutomatically = true)
    @Query("update Post p set p.like_count = p.like_count + 1 where p.id = :id")
    int increaseHeart(@Param("id") Long id);

    @Modifying(clearAutomatically = true)
    @Query("update Post p set p.like_count = p.like_count - 1 where p.id = :id")
    int decreaseHeart(@Param("id") Long id);

    @Query(value = "select p from Post p " +
            "left join fetch p.member " +
            "where p.id < :cursor",
    countQuery = "select count(p) from Post p")
    Page<Post> findPostCursorBasePaging(Long cursor, Pageable pageable);

    @Query(value = "select p from Post p " +
            "left join fetch p.member",
    countQuery = "select count(p) from Post p")
    Page<Post> findPostCursorBasePagingFirst(Pageable pageable);

    @Query("select p from Post p " +
            "where p.createdDate > :time " +
            "order by p.like_count desc, p.commentCount desc ")
    List<Post> findPostToHome(@Param("time") LocalDateTime time,
                              Pageable pageable);

    @Query("select p from Post p " +
            "where p.member.id = :userId")
    List<Post> findPostHistory(@Param("userId") Long userId);

}
