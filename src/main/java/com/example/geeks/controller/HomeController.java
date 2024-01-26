package com.example.geeks.controller;

import com.example.geeks.Security.Util;
import com.example.geeks.responseDto.HomeMainDTo;
import com.example.geeks.responseDto.HomeRealTimePostDTO;
import com.example.geeks.responseDto.PointAndMemberDTO;
import com.example.geeks.service.DetailService;
import com.example.geeks.service.PointService;
import com.example.geeks.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {
    private final Util util;
    @Value("${jwt.secret}")
    private String tokenSecretKey;

    private final PointService pointService;

    private final PostService postService;

    private final DetailService detailService;

    @GetMapping("/main")
    public HomeMainDTo home(@CookieValue String token) {
        Long userId = util.getUserId(token, tokenSecretKey);

        return new HomeMainDTo(
                detailService.detailExist(userId),
                pointService.homePointList(userId),
                postService.homePostList());
    }
}
