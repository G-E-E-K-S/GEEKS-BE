package com.example.geeks.repository;

import com.example.geeks.domain.Agree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AgreeRepository extends JpaRepository<Agree, Long> {
    @Query("select a from Agree a " +
            "where a.member.id = :userId and a.suggestion.id = :suggestionId")
    Optional<Agree> findAgreeByMemberIdAndSuggestionId(@Param("userId") Long userId,
                                                       @Param("suggestionId") Long suggestionId);
}
