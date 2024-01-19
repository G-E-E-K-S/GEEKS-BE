package com.example.geeks.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.example.geeks.Security.Util;
import com.example.geeks.domain.Member;
import com.example.geeks.repository.MemberRepository;
import com.example.geeks.requestDto.ProfileEditDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    @Value("${jwt.secret}")
    private String secretKey;

    private final MemberRepository memberRepository;

    private final Util util;

    public boolean availableEmail(String email) {
        return memberRepository.findByEmail(email).isEmpty();
    }

    public boolean availableNickname(String nickname) {
        return memberRepository.findByNickname(nickname).isEmpty();
    }

    @Transactional
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Transactional
    public void editNickname(String nickname, Long id) {
        Optional<Member> result = memberRepository.findById(id);

        Member member = result.get();
        member.changeNickname(nickname);
    }

    @Transactional
    public void editIntroduction(String introduction, Long id) {
        Optional<Member> result = memberRepository.findById(id);

        Member member = result.get();
        member.changeIntroduction(introduction); // 변경감지
    }

    @Transactional
    public void editProfile(ProfileEditDTO dto, Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Could not found id : " + id));

        member.changeProfile(dto);
    }

    public String createToken(Long id, String nickname){
        return util.createJwt(id, nickname, secretKey);
    }
}
