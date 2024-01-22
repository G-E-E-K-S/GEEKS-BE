package com.example.geeks.controller;

import com.example.geeks.Security.Util;
import com.example.geeks.responseDto.PointAndMemberDTO;
import com.example.geeks.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/point")
@RequiredArgsConstructor
public class PointController {

    private final Util util;

    @Value("${jwt.secret}")
    private String tokenSecretKey;

    private final PointService pointService;

    @GetMapping("/find")
    public List<PointAndMemberDTO> point(@CookieValue String token) {
        Long userId = util.getUserId(token, tokenSecretKey);
        return pointService.allPoint(userId);
    }
}
