package com.example.geeks.controller;

import com.example.geeks.Security.Util;
import com.example.geeks.responseDto.PointAndMemberDTO;
import com.example.geeks.responseDto.RoomMateDTO;
import com.example.geeks.responseDto.RoomMateDetailDTO;
import com.example.geeks.service.MemberService;
import com.example.geeks.service.PointService;
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

    private final MemberService memberService;

    private final PointService pointService;
    private final Util util;

    @GetMapping("/request")
    public void requestRoomMate(@RequestParam String yourNickname,
                                @CookieValue("token") String token){
        String myNickname = util.getNickname(token, secretKey);
        roomMateService.saveRoomMate(myNickname, yourNickname);
    }

    //내가 보낸사람
    @GetMapping("/sent")
    public List<RoomMateDTO> sentRoomMateList(@CookieValue("token") String token){
        Long id = util.getUserId(token, secretKey);
        List<RoomMateDTO> roomMates = roomMateService.findSentRoomMateList(id);
        return roomMates;
    }

    //나에게 보낸사람
    @GetMapping("/recived")
    public List<RoomMateDTO> recivedRoomMateList(@CookieValue("token") String token){
        Long id = util.getUserId(token, secretKey);
        List<RoomMateDTO> roomMates = roomMateService.findRecivedRoomMateList(id);
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

    @GetMapping("/save")
    public void saveRoomMate(@RequestParam String yourNickname,
                             @CookieValue("token") String token){
        String myNickname = util.getNickname(token, secretKey);
        roomMateService.saveRoomMateList(myNickname, yourNickname);
    }

    @GetMapping("/savelist")
    public List<PointAndMemberDTO> getSaveList(@CookieValue("token") String token){
        Long myId = util.getUserId(token, secretKey);
        return pointService.getSaveRoomMateList(myId);
    }

    @GetMapping("/removesave")
    public void removeSaveList(@RequestParam List<String> nickname,
                               @CookieValue("token") String token){
        String myName = util.getNickname(token, secretKey);
        for(String opponentName : nickname){
            roomMateService.removeSaveList(myName, opponentName);
        }
    }
}
