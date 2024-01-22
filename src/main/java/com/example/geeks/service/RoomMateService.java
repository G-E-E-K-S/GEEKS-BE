package com.example.geeks.service;

import com.example.geeks.domain.Detail;
import com.example.geeks.domain.Member;
import com.example.geeks.domain.RoomMate;
import com.example.geeks.repository.DetailRepository;
import com.example.geeks.repository.MemberRepository;
import com.example.geeks.repository.RoomMateRepository;
import com.example.geeks.requestDto.ChatRoomDTO;
import com.example.geeks.responseDto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RoomMateService {
    private final RoomMateRepository roomMateRepository;
    private final MemberRepository memberRepository;
    private final DetailRepository detailRepository;

    @Transactional
    public void saveRoomMate(String myNickName, String yourNickName){
        Member me = findMember(myNickName);
        Member you = findMember(yourNickName);
        RoomMate roomMate = new RoomMate(me, you);
        roomMateRepository.save(roomMate);
    }

    public Member findMember(String nickname){
        List<Member> members = memberRepository.findByNickname(nickname);
        if (!members.isEmpty()) {
            return members.get(0);
        } else {
            return null;
        }
    }

    public List<RoomMateDTO> findSentRoomMateList(String nickName){
        Member me = findMember(nickName);
        List<RoomMate> roommates = roomMateRepository.findSentByMember(me); //보낸 사람 목록이 받아짐.

        List<RoomMateDTO> roomMateDTOS = roommates
                .stream()
                .map(roomMate -> new RoomMateDTO(
                    roomMate.getReceived().getNickname(),
                        roomMate.getReceived().getMajor(),
                        roomMate.getReceived().getIntroduction(),
                        roomMate.getReceived().getPhotoName(),
                        roomMate.getReceived().getStudentID())).toList();

        return roomMateDTOS;
    }

    public List<RoomMateDTO> findRecivedRoomMateList(String nickName){
        Member me = findMember(nickName);
        List<RoomMate> roommates = roomMateRepository.findRecivedByMember(me);

        List<RoomMateDTO> roomMateDTOS = roommates
                .stream()
                .map(roomMate -> new RoomMateDTO(
                        roomMate.getSent().getNickname(),
                        roomMate.getSent().getMajor(),
                        roomMate.getSent().getIntroduction(),
                        roomMate.getSent().getPhotoName(),
                        roomMate.getSent().getStudentID())).toList();

        return roomMateDTOS;
    }
    @Transactional
    public void cancelRequest(String myNickName, String yourNickName){
        Member me = findMember(myNickName);
        Member you = findMember(yourNickName);
        roomMateRepository.deleteBySentAndReceived(me, you);
    }

    public RoomMateDetailDTO getRoomMateDetail(String nickName){
        Member member = findMember(nickName);
        Detail detail = findDetail(member.getId());


        return new RoomMateDetailDTO(member.getNickname(),
                member.getMajor(),
                member.getIntroduction(),
                member.getPhotoName(),
                member.getStudentID(),
                detail.isSmoking(),
                detail.isHabit(),
                detail.getEar(),
                detail.getSleeping(),
                detail.getWakeup(),
                detail.getOuting(),
                detail.getCleaning(),
                detail.getTendency());
    }

    public Detail findDetail(Long id){
        List<Detail> details = detailRepository.findByMemberId(id);
        if (!details.isEmpty()) {
            return details.get(0);
        } else {
            return null;
        }
    }
}
