package com.example.geeks.controller;

import com.example.geeks.Security.Util;
import com.example.geeks.requestDto.PostCommentRequestDTO;
import com.example.geeks.requestDto.PostCreateRequestDTO;
import com.example.geeks.responseDto.PostCommentResponseDTO;
import com.example.geeks.service.PostService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    private final Util util;

    @Value("${jwt.secret}")
    private String tokenSecretKey;

    @PostMapping("/create")
    public String create(
            @RequestPart(value = "files", required=false) List<MultipartFile> files,
            @RequestPart(value = "dto") PostCreateRequestDTO requestDTO,
            @CookieValue(value = "token") String token) throws IOException {
        // 쿠키값 가져와서 멤버ID 넘겨주는거로 바꾸기
        Long userId = util.getUserId(token, tokenSecretKey);
        postService.createPost(userId, requestDTO.getTitle(), requestDTO.getContent(), files);
        return "success";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam Long postId) {
        postService.deletePost(postId);
        return "success";
    }

    @PostMapping("/comment")
    public String comment(@RequestBody PostCommentRequestDTO requestDTO) {
        postService.createComment(1L, requestDTO);
        return "success";
    }

    @GetMapping("/select/comment")
    public List<PostCommentResponseDTO> selectComment(Long postId) {
        return postService.selectComment(postId);
    }
}
