package com.example.geeks.service;

import com.example.geeks.domain.Detail;
import com.example.geeks.domain.Member;
import com.example.geeks.repository.DetailRepository;
import com.example.geeks.repository.MemberRepository;
import com.example.geeks.requestDto.DetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DetailService {
    private final DetailRepository detailRepository;
    private final MemberRepository memberRepository;

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
}
