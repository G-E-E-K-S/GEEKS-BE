package com.example.geeks.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.example.geeks.Security.Util;
import com.example.geeks.domain.Member;
import com.example.geeks.repository.MemberRepository;
import com.example.geeks.requestDto.LoginDTO;
import com.example.geeks.requestDto.ProfileEditDTO;
import com.example.geeks.responseDto.MyPageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static java.util.regex.Pattern.matches;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    @Value("${jwt.secret}")
    private String secretKey;

    private final MemberRepository memberRepository;

    private final Util util;

    private final BCryptPasswordEncoder encoder;

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

    public MyPageDTO sendMyPage(Long userId) {
        Member member = memberRepository.findMemberFetchJoinWithDetail(userId);
        System.out.println("member = " + member);

        return MyPageDTO.builder()
                .photoName(member.getPhotoName())
                .major(member.getMajor())
                .studentID(member.getStudentID())
                .introduction(member.getIntroduction())
                .nickname(member.getNickname())
                .exist(member.getDetail() != null ? true : false)
                .build();
    }

    public String createToken(Long id, String nickname){
        return util.createJwt(id, nickname, secretKey);
    }

    public Long findId(String nickname){
        return memberRepository.findIdByNickname(nickname);
    }

    public String login(LoginDTO loginDTO){
        // 1. Id가 틀린 경우
        if(memberRepository.findByEmail(loginDTO.getEmail()).isEmpty()) return "Email Not Found";
        // 2. Pw가 틀린 경우
        Member user = memberRepository.findByEmail(loginDTO.getEmail()).get(0);
        // 사용자가 입력한 비밀번호 (rawPassword)와 암호화된 비밀번호 (hashedPassword)를 비교
        if(!matches(loginDTO.getPassword(), user.getPassword())) return "Password Not Equal";
        String nickname = user.getNickname();
        Long id = user.getId();
        return Util.createJwt(id, nickname, secretKey);
    }

    @Transactional
    public void editPassword(String encodePassword, Long userId){
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Could not found id : " + userId));

        member.changePassword(encodePassword);
    }
}
