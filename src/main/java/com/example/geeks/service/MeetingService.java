package com.example.geeks.service;

import com.example.geeks.domain.ChatRoom;
import com.example.geeks.domain.Meeting;
import com.example.geeks.domain.Member;
import com.example.geeks.repository.MeetingRepository;
import com.example.geeks.responseDto.ChatHistoryResponse;
import com.example.geeks.responseDto.MeetingResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MeetingService {
    private final MeetingRepository meetingRepository;

    @Transactional
    public void createRoom(String name){
        Meeting meeting = new Meeting(UUID.randomUUID().toString(), name);
        meetingRepository.save(meeting);
    }


    public List<MeetingResponseDTO> getRooms(){
        List<Meeting> meetings = meetingRepository.findAll();

        List<MeetingResponseDTO> meetingResponseDTOS = meetings
                .stream()
                .map(meeting ->
                        new MeetingResponseDTO(
                                meeting.getName(),
                                meeting.getRoomId())).toList();

        return meetingResponseDTOS;
    }
}
