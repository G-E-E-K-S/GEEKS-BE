package com.example.geeks.repository;

import com.example.geeks.Enum.SuggestionState;
import com.example.geeks.domain.Suggestion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query(value = "select s from Suggestion s " +
            "where s.id < :cursor",
            countQuery = "select count(s) from Suggestion s")
    Page<Suggestion> findSuggestionCursorBasePaging(@Param("cursor") Long cursor,
                                                    Pageable pageable);

    @Query(value = "select s from Suggestion s ",
            countQuery = "select count(s) from Suggestion s")
    Page<Suggestion> findSuggestionCursorBasePagingFirst(Pageable pageable);

    @Query(value = "select s from Suggestion s " +
            "where s.id < :cursor and s.suggestionState = :state",
            countQuery = "select count(s) from Suggestion s")
    Page<Suggestion> findSuggestionCursorFilter(@Param("cursor") Long cursor,
                                                    @Param("state") SuggestionState state,
                                                    Pageable pageable);

    @Query(value = "select s from Suggestion s where s.suggestionState = :state",
            countQuery = "select count(s) from Suggestion s")
    Page<Suggestion> findSuggestionCursorFilterFirst(Pageable pageable,
                                                     @Param("state") SuggestionState state);
}
