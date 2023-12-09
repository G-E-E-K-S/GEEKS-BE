package com.example.geeks.service;

import com.example.geeks.domain.Detail;
import com.example.geeks.domain.Member;
import com.example.geeks.domain.Point;
import com.example.geeks.repository.DetailRepository;
import com.example.geeks.repository.MemberRepository;
import com.example.geeks.repository.PointRepository;
import com.example.geeks.requestDto.DetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DetailService {
    private final DetailRepository detailRepository;
    private final MemberRepository memberRepository;
    private final PointRepository pointRepository;

    @Transactional
    public void register(Long userId, DetailDto dto) {
        Member member = memberRepository.findById(userId).get();

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
    public void calculate(Long userId) {
        List<Detail> detailList = detailRepository.findAll();
        Detail myDetail = detailRepository.findById(userId).get();

        for (Detail detail : detailList) {
            if(!detail.getMember().getId().equals(userId)) {
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

                Point result = Point.builder()
                        .member(myDetail.getMember())
                        .friend_id(detail.getMember().getId())
                        .point(point)
                        .build();

                pointRepository.save(result);
            }
        }
    }
}

