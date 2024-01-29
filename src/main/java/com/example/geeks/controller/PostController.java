package com.example.geeks.controller;

import com.example.geeks.Security.Util;
import com.example.geeks.domain.Post;
import com.example.geeks.requestDto.PostCommentRequestDTO;
import com.example.geeks.requestDto.PostCreateRequestDTO;
import com.example.geeks.responseDto.*;
import com.example.geeks.service.PostService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
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

    @GetMapping("/main")
    public PostCursorPageDTO cursorPage(@RequestParam Long cursor) {
        return postService.cursorBasePaging(cursor);
    }

    @GetMapping("/test")
    public PostCursorPageDTO test() {
        return postService.cursorBasePaging(9L);
    }

    @GetMapping("/show")
    public PostDetailDTO show(@RequestParam Long postId,
                              @CookieValue(value = "token") String token) {
        Long userId = util.getUserId(token, tokenSecretKey);
        return postService.findDetailPost(userId, postId);
    }

    @PostMapping("/delete/{postId}")
    public String delete(@PathVariable Long postId) {
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

    @PostMapping("/delete/comment/{commentId}")
    public String deleteComment(@PathVariable Long commentId) {
        postService.deleteComment(commentId);
        return "success";
    }

    @GetMapping("/heart/insert")
    public String insertHeart(@CookieValue(value = "token") String token,
                              @RequestParam Long postId) throws Exception {
        Long userId = util.getUserId(token, tokenSecretKey);
        postService.insertHeart(userId, postId);
        return "success";
    }

    @GetMapping("/heart/delete")
    public String deleteHeart(@CookieValue(value = "token") String token,
                              @RequestParam Long postId) throws Exception {
        Long userId = util.getUserId(token, tokenSecretKey);
        postService.deleteHeart(userId, postId);
        return "success";
    }

    @GetMapping("/scrap/insert")
    public String insertScrap(@CookieValue(value = "token") String token,
                              @RequestParam Long postId) throws Exception {
        Long userId = util.getUserId(token, tokenSecretKey);
        postService.insertScrap(userId, postId);
        return "success";
    }

    @GetMapping("/scrap/delete")
    public String deleteScrap(@CookieValue(value = "token") String token,
                              @RequestParam Long postId) throws Exception {
        Long userId = util.getUserId(token, tokenSecretKey);
        postService.deleteScrap(userId, postId);
        return "success";
    }

    @GetMapping("/scrap/history")
    public List<PostHistoryDTO> scrapHistory(@CookieValue(value = "token") String token) {
        Long userId = util.getUserId(token, tokenSecretKey);
        return postService.scrapList(userId);
    }

    @GetMapping("/community/history")
    public CommunityHistoryDTO communityHistory(@CookieValue(value = "token") String token) {
        Long userId = util.getUserId(token, tokenSecretKey);
        return postService.postList(userId);
    }
}
