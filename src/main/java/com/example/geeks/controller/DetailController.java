package com.example.geeks.controller;

import com.example.geeks.Security.Util;
import com.example.geeks.responseDto.DetailDto;
import com.example.geeks.service.DetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    @PostMapping("/point")
    public String calculate(@CookieValue String token){
        Long userId = util.getUserId(token, tokenSecretKey);

        detailService.calculate(userId);

        return "success";
    }

    @GetMapping("/details")
    public List<DetailDto> sendDetailToFE(@CookieValue String token,@RequestParam Long id){
        Long userId = util.getUserId(token, tokenSecretKey);

        DetailDto userDetail = detailService.getUserDetailById(userId);
        DetailDto opponentDetail = detailService.getOpponentDetailById(id);

        List<DetailDto> Details = new ArrayList<>();

        Details.add(userDetail);
        Details.add(opponentDetail);

        return Details;
    }
}
