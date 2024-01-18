package com.example.geeks.controller;

import com.example.geeks.Security.Util;
import com.example.geeks.domain.ChatRoom;
import com.example.geeks.requestDto.ChatRoomDTO;
import com.example.geeks.responseDto.ChatRoomDetailDTO;
import com.example.geeks.responseDto.MessagesResponse;
import com.example.geeks.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final Util util;

    @Value("${jwt.secret}")
    private String secretKey;

    //방만들기
    @GetMapping("/room")
    public String createRoom(@RequestParam String yournickname,
                             @CookieValue("token") String token) {
        String mynickName = util.getNickname(token, secretKey);
        System.out.print(mynickName);
        return chatService.createChatRoom(mynickName, yournickname);
    }

    //읽음 처리 기능
    /*@GetMapping("/readChat")
    public void readMessage(@RequestParam Long chatid){
        chatService.readChat(chatid);
    }*/


    //message가져오기
    @GetMapping("/messages")
    public MessagesResponse getMessages(@CookieValue("token") String token,
                                        @RequestParam String roomid,
                                        @RequestParam String yournickname){
        String myNickname = util.getNickname(token, secretKey);
        return chatService.getMessages(myNickname, roomid, yournickname);
    }

    @GetMapping("/main")
    public List<ChatRoomDTO> getChatRooms(@CookieValue("token") String token){
        String myNickname = util.getNickname(token, secretKey);
        System.out.println(myNickname);
        List<ChatRoomDTO> chatingRooms = chatService.getChatingRooms(myNickname);
        for (ChatRoomDTO chatingRoom : chatingRooms) {
            System.out.println("chatingRoom = " + chatingRoom);
        }
        return chatingRooms;
    }

    @GetMapping("/all")
    public List<ChatRoom> getAllRooms(){
        return chatService.getAllRooms();
    }

    @GetMapping("/find")
    public ChatRoomDetailDTO findRoom(@RequestParam String roomId,
                                      @CookieValue(value = "token") String token){
        String nickname = util.getNickname(token, secretKey);
        return chatService.findRoom(roomId, nickname);
    }

}
