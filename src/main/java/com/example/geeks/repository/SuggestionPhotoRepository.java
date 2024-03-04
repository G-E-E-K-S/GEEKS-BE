package com.example.geeks.repository;

import com.example.geeks.domain.SuggestionPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuggestionPhotoRepository extends JpaRepository<SuggestionPhoto, Long> {
}
