package com.example.geeks.repository;

import com.example.geeks.domain.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
    @Query("select p.photoName from Photo p where p.post.id = :postId")
    List<String> findPhotoNamesByPostId(@Param("postId") Long postId);
}
