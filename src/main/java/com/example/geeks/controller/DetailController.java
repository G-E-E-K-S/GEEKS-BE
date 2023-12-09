package com.example.geeks.controller;

import com.example.geeks.Security.Util;
import com.example.geeks.requestDto.DetailDto;
import com.example.geeks.service.DetailService;
import com.example.geeks.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/detail")
@RequiredArgsConstructor
public class DetailController {
    private final DetailService detailService;


    private final Util util;
    @Value("${jwt.secret}")
    private String tokenSecretKey;

    @PostMapping("/register")
    public String register(@CookieValue String token, @RequestBody DetailDto dto) {
        Long userId = util.getUserId(token, tokenSecretKey);
        //Long userId = 1L;

        detailService.register(userId, dto);
        
        return "success";
    }
}
