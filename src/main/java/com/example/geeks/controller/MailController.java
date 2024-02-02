package com.example.geeks.controller;

import com.example.geeks.Security.Util;
import com.example.geeks.service.MailAuthService;
import com.example.geeks.service.MailService;
import com.example.geeks.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
public class MailController {
    private final MailService mailService;

    private final MemberService memberService;

    private final MailAuthService emailAuthService;

    private final Util util;

    @Value("${jwt.secret}")
    private String tokenSecretKey;

    private final BCryptPasswordEncoder encoder;

    @GetMapping("/send")
    public String mailConfirm(@RequestParam String email, HttpSession session) throws Exception{
        // 비어있으면 true 있다면 false
        boolean pass = memberService.availableEmail(email);

        if(!pass) return "duplicate";

        session.setAttribute("email", email);

        String code = mailService.sendSimpleMessage(email);
        System.out.println("인증코드 : " + code);

        emailAuthService.saveDataWithExpiration(email, code, 300);
        return code;
    }

    @GetMapping("/auth")
    public String mailAuth(@RequestParam String code, HttpSession session) {
        String email = (String) session.getAttribute("email");

        try {
            if(emailAuthService.getData(email).equals(code)) {
                session.setAttribute("nickname", email);
                return "success!";
            } else {
                return "false";
            }
        } catch (NullPointerException e) {
            System.out.println("인증 코드 시간이 만료되었습니다.");
            return "timeout";
        }
    }

    @GetMapping("/temporary")
    public String mailTemporary(@RequestParam String email,
                                @CookieValue String token,
                                HttpSession session) throws Exception{
        boolean pass = memberService.availableEmail(email);
        if(pass) return "duplicate";

        session.setAttribute("email", email);

        String password = mailService.sendPasswordMessage(email);

        Long userId = util.getUserId(token, tokenSecretKey);
        String encodePassword = encoder.encode(password);
        session.setAttribute("password", encodePassword);
        memberService.editPassword(encodePassword, userId);

        return "success";
    }
}
