package com.example.geeks.controller;

import com.example.geeks.Enum.DormitoryType;
import com.example.geeks.domain.Member;
import com.example.geeks.requestDto.PasswordDto;
import com.example.geeks.requestDto.RegisterDto;
import com.example.geeks.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class MemberController {
    private final MemberService memberService;

    private final BCryptPasswordEncoder encoder;

    @GetMapping("/register")
    public String register(HttpSession session) {
        Member member = Member.builder()
                .nickname((String) session.getAttribute("nickname"))
                .email((String) session.getAttribute("email"))
                .password((String) session.getAttribute("password"))
                .major((String) session.getAttribute("major"))
                .gender((int) session.getAttribute("gender"))
                .exp((int) session.getAttribute("exp"))
                .type((DormitoryType) session.getAttribute("type"))
                .image_url("basic")
                .introduction("")
                .build();
        memberService.join(member);
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
    public String gender(@RequestParam int gender, HttpSession session) {
        session.setAttribute("gender", gender);
        return "success";
    }

    @GetMapping("/type")
    public String type(@RequestParam DormitoryType type, HttpSession session) {
        session.setAttribute("type", type);
        return "success";
    }

    @GetMapping("/edit/nickname")
    public String editNickname(@RequestParam String nickname) {
        return "success";
    }

    @GetMapping("/edit/introduction")
    public String editIntroduction(@RequestParam String introduction) {
        return "success";
    }
}
