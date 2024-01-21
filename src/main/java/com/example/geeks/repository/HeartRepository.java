package com.example.geeks.repository;

import com.example.geeks.domain.Heart;
import com.example.geeks.domain.Member;
import com.example.geeks.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface HeartRepository extends JpaRepository<Heart, Long> {

    Optional<Heart> findByMemberAndPost(@Param("member")Member member, @Param("post")Post post);
}
