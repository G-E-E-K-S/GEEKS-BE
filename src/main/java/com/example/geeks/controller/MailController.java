package com.example.geeks.controller;

import com.amazonaws.services.kms.model.NotFoundException;
import com.example.geeks.Security.Util;
import com.example.geeks.domain.Member;
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
import java.util.Optional;

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
        Optional<Member> member = memberService.availableEmail(email);

        if(member.isPresent()) return "duplicate";

        session.setAttribute("email", email);

        String code = mailService.sendSimpleMessage(email);
        System.out.println("인증코드 : " + code);

        emailAuthService.saveDataWithExpiration(email, code, 180);
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
    public String mailTemporary(@RequestParam String email) throws Exception{
        Optional<Member> member = memberService.availableEmail(email);
        if(!member.isPresent()) return "duplicate";

        String password = mailService.sendPasswordMessage(email);

        String encodePassword = encoder.encode(password);
        memberService.editPassword(encodePassword, member.get().getId());
        return "success";
    }
}
