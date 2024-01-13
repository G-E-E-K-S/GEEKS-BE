package com.example.geeks.repository;

import com.example.geeks.responseDto.PostCommentResponseDTO;

import java.util.List;

public interface CommentRepositoryCustom {
    List<PostCommentResponseDTO> findByPostId(Long postId);
}
