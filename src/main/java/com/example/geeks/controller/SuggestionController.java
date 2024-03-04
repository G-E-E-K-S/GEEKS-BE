package com.example.geeks.controller;

import com.example.geeks.Security.Util;
import com.example.geeks.requestDto.PostCreateRequestDTO;
import com.example.geeks.requestDto.SuggestionCreateDTO;
import com.example.geeks.service.SuggestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/suggestion")
@RequiredArgsConstructor
public class SuggestionController {

    private final Util util;

    private final SuggestionService suggestionService;

    @Value("${jwt.secret}")
    private String tokenSecretKey;

    @PostMapping("/create")
    public String create(
            @RequestPart(value = "files", required=false) List<MultipartFile> files,
            @RequestPart(value = "dto") SuggestionCreateDTO requestDTO,
            @CookieValue(value = "token") String token) throws IOException {

        Long userId = util.getUserId(token, tokenSecretKey);
        suggestionService.createPost(userId, requestDTO, files);
        return "success";
    }
}
