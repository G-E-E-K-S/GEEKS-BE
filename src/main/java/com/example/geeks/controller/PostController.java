package com.example.geeks.controller;

import com.example.geeks.Security.Util;
import com.example.geeks.requestDto.PostCommentRequestDTO;
import com.example.geeks.requestDto.PostCreateRequestDTO;
import com.example.geeks.responseDto.PostAllDTO;
import com.example.geeks.responseDto.PostCommentResponseDTO;
import com.example.geeks.responseDto.PostDetailDTO;
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

        Long userId = util.getUserId(token, tokenSecretKey);
        postService.createPost(userId, requestDTO, files);
        return "success";
    }

    @GetMapping("/showAll")
    public List<PostAllDTO> showAll() {
        return postService.findAllPost();
    }

    @GetMapping("/show")
    public PostDetailDTO show(@RequestParam Long postId) {
        return postService.findDetailPost(postId);
    }

    @GetMapping("/delete")
    public String delete(@RequestParam Long postId) {
        postService.deletePost(postId);
        return "success";
    }

    @PostMapping("/comment")
    public String comment(@RequestBody PostCommentRequestDTO requestDTO,
                          @CookieValue(value = "token") String token) {
        Long userId = util.getUserId(token, tokenSecretKey);
        postService.createComment(userId, requestDTO);
        return "success";
    }

    @GetMapping("/delete/comment")
    public String deleteComment(@RequestParam Long commentId) {
        postService.deleteComment(commentId);
        return "success";
    }

    @GetMapping("/select/comment")
    public List<PostCommentResponseDTO> selectComment(Long postId) {
        return postService.selectComment(postId);
    }
}
