package com.example.geeks.controller;

import com.example.geeks.requestDto.LoginDTO;
import com.example.geeks.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class LoginController {
    private final MemberService memberService;

    @PostMapping("/login")
    public String login(@RequestBody LoginDTO loginDTO){
        String token = memberService.login(loginDTO);

        if(token == "Email Not Found" || token == "Password Not Equal"){
            System.out.println("Not user" + token + " " + loginDTO.getPassword());
            return "fail";
        }
        Cookie cookie = new Cookie("token", token);

        cookie.setPath("/");
        cookie.setSecure(false);
        cookie.setMaxAge(86400); //1일
        cookie.setHttpOnly(false);

        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        response.addCookie(cookie);

        return "success";
    }
}