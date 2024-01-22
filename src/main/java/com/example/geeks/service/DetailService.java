package com.example.geeks.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.example.geeks.domain.Detail;
import com.example.geeks.domain.Member;
import com.example.geeks.domain.Point;
import com.example.geeks.repository.DetailRepository;
import com.example.geeks.repository.MemberRepository;
import com.example.geeks.repository.PointRepository;
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

    @Transactional
    public void register(Long userId, DetailDTO dto) {
        Member member = memberRepository.findById(userId)
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

    @Transactional
    public void calculate(Long userId) {

        Detail myDetail = detailRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Could not found id : " + userId));

        List<Long> friendIds = new ArrayList<>();
        friendIds.add(userId);

        List<Point> points = pointRepository.findByMemberIdFetch(userId);

        for (Point point : points) {
            friendIds.add(point.getFriend().getId());

            if(point.getLastModifiedDate().isAfter(myDetail.getLastModifiedDate()) ||
                point.getLastModifiedDate().isAfter(point.getFriend().getLastModifiedDate())) {
                point.setPoint(pointCalculator(myDetail, point.getFriend().getDetail()));
            }
        }

        List<Detail> detailList = detailRepository.findListNotInFriendId(friendIds);

        for (Detail detail : detailList) {
            Point result = Point.builder()
                    .member(myDetail.getMember())
                    .friend(detail.getMember())
                    .point(pointCalculator(myDetail, detail))
                    .build();

            pointRepository.save(result);
        }
    }

    public int pointCalculator(Detail myDetail, Detail detail) {
        int count = 0, point = 0;

        if(detail.isHabit() == myDetail.isHabit()) count++;
        if(detail.getEar().equals(myDetail.getEar())) count++;
        if(detail.isSmoking() == myDetail.isSmoking()) count++;
        if(detail.getCleaning().equals(myDetail.getCleaning())) count++;
        if(detail.getOuting().equals(myDetail.getOuting())) count++;
        if(detail.getTendency().equals(myDetail.getTendency())) count++;
        if(detail.getWakeup().equals(myDetail.getWakeup())) count++;
        if(detail.getSleeping().equals(myDetail.getSleeping())) count++;

        switch (count){
            case 1:
                point = 10;
                break;
            case 2:
                point = 20;
                break;
            case 3:
                point = 30;
                break;
            case 4:
                point = 40;
                break;
            case 5:
                point = 55;
                break;
            case 6:
                point = 70;
                break;
            case 7:
                point = 85;
                break;
            case 8:
                point = 100;
                break;
        }

        return point;
    }

    public void sendDetails(Long myId, Long opponentId) {
        DetailDTO myDetail = getUserDetailById(myId);
        DetailDTO opponentDetail = getUserDetailById(opponentId);
    }

    public DetailDTO getUserDetailById(Long userId){
        Detail userDetail = detailRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Could not found id : " + userId));

        DetailDTO userDetailDTO =
                new DetailDTO(userDetail.getId(), userDetail.isSmoking(), userDetail.isHabit(),
                userDetail.getEar(), userDetail.getSleeping(), userDetail.getWakeup(),
                userDetail.getOuting(), userDetail.getCleaning(), userDetail.getTendency());

        return userDetailDTO;
    }

}

