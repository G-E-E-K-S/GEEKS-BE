package com.example.geeks.controller;

import com.example.geeks.Security.Util;
import com.example.geeks.responseDto.DetailDTO;
import com.example.geeks.responseDto.DetailResponseDTO;
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
    public String register(@CookieValue String token, @RequestBody DetailDTO dto) {
        Long userId = util.getUserId(token, tokenSecretKey);

        detailService.register(userId, dto);
        
        return "success";
    }

    @PostMapping("/update")
    public String update(@CookieValue String token, @RequestBody DetailDTO dto) {
        detailService.update(dto);
        return "success";
    }

    @GetMapping("/send")
    public DetailResponseDTO sendDetail(@CookieValue String token) {
        Long userId = util.getUserId(token, tokenSecretKey);
        return detailService.sendDetail(userId);
    }

    @GetMapping("/details")
    public List<DetailDTO> sendDetails(@CookieValue String token, @RequestParam Long id){
        Long userId = util.getUserId(token, tokenSecretKey);

        return detailService.sendDetails(userId, id);
    }
}
