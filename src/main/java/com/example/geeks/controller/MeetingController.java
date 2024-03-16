package com.example.geeks.controller;

import com.example.geeks.domain.Meeting;
import com.example.geeks.responseDto.MeetingResponseDTO;
import com.example.geeks.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/meeting")
@RequiredArgsConstructor
public class MeetingController {

    private final MeetingService meetingService;

    @GetMapping("/create")
    public String createRoom(@RequestParam String name){
        meetingService.createRoom(name);
        return "Success";
    }

    @GetMapping("/rooms")
    public List<MeetingResponseDTO> rooms(){
        return meetingService.getRooms();
    }
}
