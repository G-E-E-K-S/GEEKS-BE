package com.example.geeks.controller;

import com.example.geeks.Enum.DormitoryType;
import com.example.geeks.Enum.Gender;
import com.example.geeks.Security.Util;
import com.example.geeks.domain.Member;
import com.example.geeks.requestDto.PasswordDto;
import com.example.geeks.requestDto.ProfileEditDto;
import com.example.geeks.requestDto.RegisterDto;
import com.example.geeks.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    private final BCryptPasswordEncoder encoder;

    private final Util util;

    @Value("${jwt.secret}")
    private String tokenSecretKey;

   /* @GetMapping("/register")
    public String register(HttpSession session) {
        Member member = Member.builder()
                .nickname((String) session.getAttribute("nickname"))
                .email((String) session.getAttribute("email"))
                .password((String) session.getAttribute("password"))
                .major((String) session.getAttribute("major"))
                .gender((Gender) session.getAttribute("gender"))
                .exp((int) session.getAttribute("exp"))
                .type((DormitoryType) session.getAttribute("type"))
                .image_url("basic")
                .introduction("")
                .build();
        memberService.join(member);

        String token = memberService.createToken(member.getId(), member.getNickname());
        Cookie cookie = new Cookie("token", token);

        cookie.setPath("/");
        cookie.setSecure(false);
        cookie.setMaxAge(86400); // 1일
        cookie.setHttpOnly(false);

        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        response.addCookie(cookie);

        return "";
    }*/
   @PostMapping("/register")
   public String register(@RequestBody RegisterDto dto) {
       Member member = Member.builder()
               .nickname(dto.getNickname())
               .email(dto.getEmail())
               .password(dto.getPassword())
               .major(dto.getMajor())
               .gender(dto.getGender())
               .exp(dto.getExp())
               .type(dto.getType())
               .image_url("basic")
               .introduction("")
               .build();

       memberService.join(member);

       String token = memberService.createToken(member.getId(), member.getNickname());
       Cookie cookie = new Cookie("token", token);

       cookie.setPath("/");
       cookie.setSecure(false);
       cookie.setMaxAge(86400); // 1일
       cookie.setHttpOnly(false);

       HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
       response.addCookie(cookie);

       return "";
   }

    @GetMapping("/check/nickname")
    public String checkNickname(@RequestParam String nickname) {
        if(!memberService.availableNickname(nickname)) return "duplicate";
        return "available";
    }

    @PostMapping("/password")
    public String password(@RequestBody PasswordDto dto, HttpSession session) {
        String encodePassword = encoder.encode(dto.getPassword());
        session.setAttribute("password", encodePassword);
        return "success";
    }

    @GetMapping("/nickname")
    public String nickname(@RequestParam String nickname, HttpSession session) {
        session.setAttribute("nickname", nickname);
        return "success";
    }

    @GetMapping("/major")
    public String major(@RequestParam("major") String major,
                        @RequestParam("studentID") int studentID, HttpSession session) {
        session.setAttribute("major", major);
        session.setAttribute("studentID", studentID);
        return "success";
    }

    @GetMapping("/gender")
    public String gender(@RequestParam Gender gender, HttpSession session) {
        session.setAttribute("gender", gender);
        return "success";
    }

    @GetMapping("/type")
    public String type(@RequestParam DormitoryType type, HttpSession session) {
        session.setAttribute("type", type);
        return "success";
    }

    @GetMapping("/edit/nickname")
    public String editNickname(@RequestParam String nickname,
                               @CookieValue String token) {
        Long userId = util.getUserId(token, tokenSecretKey);
        memberService.editNickname(nickname, userId);
        return "success";
    }

    @GetMapping("/edit/introduction")
    public String editIntroduction(@RequestParam String introduction,
                                   @CookieValue String token) {
        Long userId = util.getUserId(token, tokenSecretKey);
        memberService.editIntroduction(introduction, userId);
        return "success";
    }

    @PostMapping("/edit/profile")
    public String editProfile(@RequestBody ProfileEditDto dto,
                              @CookieValue String token) {
        Long userId = util.getUserId(token, tokenSecretKey);
        memberService.editProfile(dto, userId);
        return "success";
    }
}
