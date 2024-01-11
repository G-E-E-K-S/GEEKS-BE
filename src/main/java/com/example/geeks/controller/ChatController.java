package com.example.geeks.controller;

import com.example.geeks.Security.Util;
import com.example.geeks.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class ChatController {
    @GetMapping("/")
    public String index(){
        return "error";
    }
    private final ChatService chatService;
    private final Util util;

    @Value("${jwt.secret}")
    private String secretKey;

    //방만들기
    @GetMapping("/room")
    public String createRoom(@RequestParam String yournickname, @CookieValue("token") String token) {
        String mynickName = util.getNickname(token, secretKey);
        System.out.print(mynickName);
        return chatService.createChatRoom(mynickName, yournickname);
    }

    @GetMapping("/{id}")
    public String chattingRoom(@PathVariable String id, HttpSession session, Model model){
        if(id.equals("guest")){
            model.addAttribute("name", "guest");
        }else if(id.equals("master")){
            model.addAttribute("name", "master");
        }else if(id.equals("loose")){
            model.addAttribute("name", "loose");
        }else {
            return "error";
        }
        return "chattingRoom2";
    }
}
