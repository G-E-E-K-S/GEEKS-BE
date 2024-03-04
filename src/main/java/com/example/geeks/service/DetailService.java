package com.example.geeks.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.example.geeks.domain.Detail;
import com.example.geeks.domain.Member;
import com.example.geeks.domain.Point;
import com.example.geeks.repository.*;
import com.example.geeks.responseDto.DetailCompareDTO;
import com.example.geeks.responseDto.DetailDTO;
import com.example.geeks.responseDto.DetailResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DetailService {
    private final DetailRepository detailRepository;
    private final MemberRepository memberRepository;
    private final PointRepository pointRepository;
    private final SaveRoomMateRepository saveRoomMateRepository;
    private final AcceptRoomMateRepository acceptRoomMateRepository;
    private final RoomMateRepository roomMateRepository;

    @Transactional
    public void register(Long userId, DetailDTO dto) {
        Member member = memberRepository.findByIdFetchDetail(userId)
                .orElseThrow(() -> new NotFoundException("Could not found id : " + userId));

        Detail detail = Detail.builder()
                .ear(dto.getEar())
                .sleep(dto.getSleep())
                .smoking(dto.isSmoking())
                .out(dto.getOut())
                .habit(dto.isHabit())
                .wakeup(dto.getWakeup())
                .cleaning(dto.getCleaning())
                .tendency(dto.getTendency())
                .member(member)
                .build();

        detailRepository.save(detail);
    }

    @Transactional
    public void update(DetailDTO dto) {
        Detail detail = detailRepository.findById(dto.getDetailId())
                .orElseThrow(() -> new NotFoundException("Could not found id : " + dto.getDetailId()));

        detail.setCleaning(dto.getCleaning());
        detail.setEar(dto.getEar());
        detail.setHabit(dto.isHabit());
        detail.setOuting(dto.getOut());
        detail.setSmoking(dto.isSmoking());
        detail.setSleeping(dto.getSleep());
        detail.setTendency(dto.getTendency());
        detail.setWakeup(dto.getWakeup());
    }

    public boolean detailExist(Long userId) {
        Optional<Detail> detail = detailRepository.findDetailByMemberId(userId);

        if(detail.isPresent()) {
            return true;
        }

        return false;
    }

    public DetailResponseDTO sendDetail(Long userId) {
        List<Detail> details = detailRepository.findByMemberId(userId);
        DetailResponseDTO result;

        if(details.isEmpty()) {
            result = new DetailResponseDTO(false);
        }
        else {
            Detail detail = details.get(0);

            result = DetailResponseDTO.builder()
                    .detailId(detail.getId())
                    .smoking(detail.isSmoking())
                    .habit(detail.isHabit())
                    .ear(detail.getEar())
                    .sleep(detail.getSleeping())
                    .wakeup(detail.getWakeup())
                    .out(detail.getOuting())
                    .cleaning(detail.getCleaning())
                    .tendency(detail.getTendency())
                    .exist(true)
                    .build();
        }

        return result;
    }

    public DetailCompareDTO sendDetails(Long myId, Long opponentId) {
        List<DetailDTO> detailDTOS = new ArrayList<>();

        DetailDTO myDetail = getUserDetailById(myId);
        DetailDTO opponentDetail = getUserDetailById(opponentId);

        if(!saveRoomMateRepository.findByMeIdAndYouId(myId, opponentId).isEmpty()) {
            myDetail.setSaved(true);
            opponentDetail.setSaved(true);
        }

        detailDTOS.add(myDetail);
        detailDTOS.add(opponentDetail);

        Member opponent = memberRepository.findMemberFetchDetail(opponentId)
                .orElseThrow(() -> new NotFoundException("Could not found id : " + opponentId));

        int point = pointRepository.findPointByMemberIdAndFriendId(myId, opponentId);

        boolean roommateApply = false;
        boolean roommateState = false;
        boolean acceptRoommate = false;

        if(roomMateRepository.findRoomMateState(myId, opponentId).isPresent()) {
            roommateApply = true;
        }

        if(!roommateApply && acceptRoomMateRepository.findAcceptRoomMate(myId, opponentId).isPresent()) {
            roommateState = true;
        }

        if(acceptRoomMateRepository.findAcceptRoomMateState(myId).isPresent()) {
            acceptRoommate = true;
        }

        return DetailCompareDTO.builder()
                .point(point)
                .details(detailDTOS)
                .major(opponent.getMajor())
                .roommateApply(roommateApply)
                .roommateState(roommateState)
                .acceptRoommate(acceptRoommate)
                .nickname(opponent.getNickname())
                .photoName(opponent.getPhotoName())
                .studentID(opponent.getStudentID())
                .introduction(opponent.getIntroduction())
                .build();
    }

    public DetailDTO getUserDetailById(Long userId){
        Detail userDetail = detailRepository.findDetailByMemberId(userId)
                .orElseThrow(() -> new NotFoundException("Could not found id : " + userId));

        DetailDTO userDetailDTO =
                new DetailDTO(userDetail.getId(), userDetail.isSmoking(), userDetail.isHabit(),
                userDetail.getEar(), userDetail.getSleeping(), userDetail.getWakeup(),
                userDetail.getOuting(), userDetail.getCleaning(), userDetail.getTendency(), false);

        return userDetailDTO;
    }


}

