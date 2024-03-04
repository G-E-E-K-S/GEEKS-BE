package com.example.geeks.repository;

import com.example.geeks.domain.Suggestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SuggestionRepository extends JpaRepository<Suggestion, Long> {

    @Modifying(clearAutomatically = true)
    @Query("update Suggestion s set s.agree_count = s.agree_count + 1 where s.id = :id")
    int increaseAgree(@Param("id") Long id);

    @Modifying(clearAutomatically = true)
    @Query("update Suggestion s set s.agree_count = s.agree_count - 1 where s.id = :id")
    int decreaseAgree(@Param("id") Long id);
}
