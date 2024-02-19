package com.example.geeks.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.example.geeks.domain.*;
import com.example.geeks.repository.*;
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
    private final SaveRoomMateRepository saveRoomMateRepository;

    private final AcceptRoomMateRepository acceptRoomMateRepository;

    @Transactional
    public void saveRoomMate(String myNickName, String yourNickName) {
        Member me = findMember(myNickName);
        Member you = findMember(yourNickName);

        if(me == null || you == null) {
            return;
        }

        RoomMate existingRoomMate = roomMateRepository.findBySentAndReceived(me, you);

        if (existingRoomMate == null) {
            RoomMate roomMate = new RoomMate(me, you);
            roomMateRepository.save(roomMate);
        } else {
            System.out.println("이미 저장되어있음");
        }
    }


    public Member findMember(String nickname){
        List<Member> members = memberRepository.findByNickname(nickname);
        if (!members.isEmpty()) {
            return members.get(0);
        } else {
            return null;
        }
    }

    public List<RoomMateDTO> findSentRoomMateList(Long id) {
        List<RoomMate> roommates = roomMateRepository.findSentByMemberId(id);

        List<RoomMateDTO> roomMateDTOS = roommates
                .stream()
                .map(roomMate -> new RoomMateDTO(
                        roomMate.getReceived().getId(),
                        roomMate.getReceived().getNickname(),
                        roomMate.getReceived().getMajor(),
                        roomMate.getReceived().getIntroduction(),
                        roomMate.getReceived().getPhotoName(),
                        roomMate.getReceived().getStudentID(),
                        roomMate.getCreatedDate()))
                .toList();

        for(RoomMateDTO roomMateDTO : roomMateDTOS){
            System.out.println(roomMateDTO.getNickname());
        }

        return roomMateDTOS;
    }

    public List<RoomMateDTO> findRecivedRoomMateList(Long id) {
        List<RoomMate> roommates = roomMateRepository.findRecivedById(id);

        List<RoomMateDTO> roomMateDTOS = roommates
                .stream()
                .filter(roomMate -> roomMate.getSent() != null) // 필터 추가
                .map(roomMate -> new RoomMateDTO(
                        roomMate.getSent().getId(),
                        roomMate.getSent().getNickname(),
                        roomMate.getSent().getMajor(),
                        roomMate.getSent().getIntroduction(),
                        roomMate.getSent().getPhotoName(),
                        roomMate.getSent().getStudentID(),
                        roomMate.getCreatedDate())).toList();

        for (RoomMateDTO roomMateDTO : roomMateDTOS) {
            System.out.println(roomMateDTO.getNickname());
        }

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

    @Transactional
    public void saveRoomMateList(String myNickName, String yourNickName){
        Member me = findMember(myNickName);
        Member you = findMember(yourNickName);

        SaveRoomMate existingRoomMate = saveRoomMateRepository.findByMeAndYou(me, you);

        if (existingRoomMate == null) {
            SaveRoomMate saveRoomMate = new SaveRoomMate(me, you);
            saveRoomMateRepository.save(saveRoomMate);
        } else {
            System.out.println("이미 저장되어있음");
        }
    }

    @Transactional
    public void removeSaveList(String myName, String opponentName){
        Member me = findMember(myName);
        Member you = findMember(opponentName);
        saveRoomMateRepository.deleteByMeAndYou(me, you);
    }
    @Transactional
    public void acceptRoommate(Long acceptId, Long senderId) {
        Member accept = memberRepository.findMemberFetchDetail(acceptId)
                .orElseThrow(() -> new NotFoundException("Could not found id : " + acceptId));

        Member sender = memberRepository.findMemberFetchDetail(senderId)
                .orElseThrow(() -> new NotFoundException("Could not found id : " + senderId));

        AcceptRoomMate acceptRoomMate = new AcceptRoomMate(accept, sender);
        acceptRoomMateRepository.save(acceptRoomMate);

        accept.setOpen(false);
        sender.setOpen(false);
        roomMateRepository.deleteAllRoomMate(acceptId, senderId);
    }

    @Transactional
    public void refuseRoommate(Long myId, Long senderId) {
        roomMateRepository.deleteRoomMate(myId, senderId);
    }

    @Transactional
    public void deleteList(Long id){
        roomMateRepository.deleteRoomMateMyidOrSenderid(id);
        saveRoomMateRepository.deleteByMeOrYou(id);
        acceptRoomMateRepository.deleteAcceptRoomMateForWithdraw(id);
    }

    @Transactional
    public void quitRoommate(Long userId, Long roommateId) {
        AcceptRoomMate acceptRoomMate = acceptRoomMateRepository.findAcceptRoomMate(userId, roommateId)
                .orElseThrow(() -> new NotFoundException("Could not found id"));

        acceptRoomMateRepository.delete(acceptRoomMate);
    }

    public boolean applyRoommate(Long userId) {
        return !roomMateRepository.findRoomMateByReceivedId(userId).isEmpty();
    }

}
