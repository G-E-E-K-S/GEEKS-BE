package com.example.geeks.repository;

import com.example.geeks.domain.Member;
import com.example.geeks.domain.Post;
import com.example.geeks.domain.PostScrap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostScrapRepository extends JpaRepository<PostScrap, Long> {
    Optional<PostScrap> findByMemberAndPost(@Param("member")Member member,
                                            @Param("post")Post post);

    Optional<PostScrap> findByMemberIdAndPostId(@Param("memberId") Long memberId,
                                                @Param("postId") Long postId);
}
