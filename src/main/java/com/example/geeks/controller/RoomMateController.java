package com.example.geeks.controller;

import com.example.geeks.Security.Util;
import com.example.geeks.domain.Member;
import com.example.geeks.responseDto.MyPageDTO;
import com.example.geeks.responseDto.RoomMateDTO;
import com.example.geeks.responseDto.RoomMateDetailDTO;
import com.example.geeks.service.RoomMateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roommate")
@RequiredArgsConstructor
public class RoomMateController {
    @Value("${jwt.secret}")
    private String secretKey;

    private final RoomMateService roomMateService;

    private final Util util;

    @GetMapping("/request")
    public void requestRoomMate(@RequestParam String yourNickname,
                                @CookieValue("token") String token){
        String myNickname = util.getNickname(token, secretKey);
        roomMateService.saveRoomMate(myNickname, yourNickname);
    }

    //나에게 보낸사람
    @GetMapping("/sent")
    public List<RoomMateDTO> sentRoomMateList(@CookieValue("token") String token){
        String myNickname = util.getNickname(token, secretKey);
        List<RoomMateDTO> roomMates = roomMateService.findSentRoomMateList(myNickname);
        return roomMates;
    }

    //내가 보낸사람
    @GetMapping("/recived")
    public List<RoomMateDTO> recivedRoomMateList(@CookieValue("token") String token){
        String myNickname = util.getNickname(token, secretKey);
        List<RoomMateDTO> roomMates = roomMateService.findRecivedRoomMateList(myNickname);
        return roomMates;
    }

    @GetMapping("/remove")
    public void cancelRequest(@RequestParam String yournickname,
                              @CookieValue("token") String token){
        String mynickName = util.getNickname(token, secretKey);
        roomMateService.cancelRequest(mynickName, yournickname);
    }

    @GetMapping("/roommateDetail")
    public RoomMateDetailDTO roomMateDetail(@RequestParam String yournickname){
        RoomMateDetailDTO roomMateDetailDTO = roomMateService.getRoomMateDetail(yournickname);
        return roomMateDetailDTO;
    }
}
