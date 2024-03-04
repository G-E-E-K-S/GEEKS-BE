package com.example.geeks.repository;

import com.example.geeks.domain.SuggestionPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SuggestionPhotoRepository extends JpaRepository<SuggestionPhoto, Long> {

    @Query("select sp.photoName from SuggestionPhoto sp where sp.suggestion.id = :suggestionId")
    List<String> findPhotoNamesBySuggestionId(@Param("suggestionId") Long suggestionId);
}
