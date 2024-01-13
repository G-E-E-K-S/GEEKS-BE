package com.example.geeks.controller;

import com.example.geeks.requestDto.PostCommentRequestDTO;
import com.example.geeks.requestDto.PostCreateRequestDTO;
import com.example.geeks.responseDto.PostCommentResponseDTO;
import com.example.geeks.service.PostService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/create")
    public String create(
            @RequestPart(required=false) List<MultipartFile> files,
            @RequestParam String title, @RequestParam String content) throws IOException {
        // 쿠키값 가져와서 멤버ID 넘겨주는거로 바꾸기
        postService.createPost(1L, title, content, files);
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
