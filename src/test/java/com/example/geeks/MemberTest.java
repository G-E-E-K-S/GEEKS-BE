package com.example.geeks;

import com.example.geeks.Enum.*;
import com.example.geeks.domain.Detail;
import com.example.geeks.domain.Member;
import com.example.geeks.repository.DetailRepository;
import com.example.geeks.repository.MemberRepository;
import com.example.geeks.responseDto.DetailDto;
import com.example.geeks.requestDto.ProfileEditDto;
import com.example.geeks.service.DetailService;
import com.example.geeks.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
@Rollback(false)
public class MemberTest {
    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;
    
    @Autowired
    DetailRepository detailRepository;
    
    @Autowired
    DetailService detailService;

    @Autowired
    EntityManager em;


    @Test
    public void testEditNickname() {
        Member member = Member.builder()
                .nickname("admin")
                .email("bak3839@naver.com")
                .password("1234")
                .major("소프트웨어")
                .gender(Gender.MALE)
                .exp(3)
                .type(DormitoryType.NEW)
                .image_url("basic")
                .introduction("")
                .build();

        memberRepository.save(member);

        Optional<Member> result1 = memberRepository.findById(1L);

        Member findMember1 = result1.get();
        System.out.println("before: " + findMember1);

        memberService.editNickname("change", 1L);

        Optional<Member> result2 = memberRepository.findById(1L);

        Member findMember2 = result2.get();
        System.out.println("after: " + findMember2);
    }
    @Test
    public void testEditIntroduction() {
        Member member = Member.builder()
                .nickname("admin")
                .email("bak3839@naver.com")
                .password("1234")
                .major("소프트웨어")
                .gender(Gender.MALE)
                .exp(3)
                .type(DormitoryType.NEW)
                .image_url("basic")
                .introduction("")
                .build();

        memberRepository.save(member);

        Optional<Member> result1 = memberRepository.findById(1L);

        Member findMember1 = result1.get();
        System.out.println("before: " + findMember1);

        memberService.editIntroduction("hi", 1L);

        Optional<Member> result2 = memberRepository.findById(1L);

        Member findMember2 = result2.get();
        System.out.println("after: " + findMember2);
    }

    @Test
    public void editProfile() {
        Member member = Member.builder()
                .nickname("admin")
                .email("bak3839@naver.com")
                .password("1234")
                .major("소프트웨어")
                .gender(Gender.MALE)
                .studentID(19)
                .exp(3)
                .type(DormitoryType.NEW)
                .image_url("basic")
                .introduction("")
                .build();

        memberRepository.save(member);

        Member member1 = memberRepository.findById(1L).get();

        System.out.println("before: " + member1);

        em.clear();
        em.flush();

        ProfileEditDto dto = new ProfileEditDto("admin", "소프트웨어", 20, "안녕");
        memberService.editProfile(dto, 1L);

        Member member2 = memberRepository.findById(1L).get();

        System.out.println("after: " + member2);
    }

    @Test
    public void testSendDetailList() {
        Member member = Member.builder()
                .nickname("admin")
                .email("bak3839@naver.com")
                .password("1234")
                .major("소프트웨어")
                .gender(Gender.MALE)
                .exp(3)
                .type(DormitoryType.NEW)
                .image_url("basic")
                .introduction("")
                .build();

        memberRepository.save(member);

        Member member1 = Member.builder()
                .nickname("90000e")
                .email("gamgam0330@naver.com")
                .password("1234")
                .major("소프트웨어")
                .gender(Gender.MALE)
                .exp(2)
                .type(DormitoryType.NEW)
                .image_url("basic")
                .introduction("")
                .build();

        memberRepository.save(member1);

        Optional<Member> result1 = memberRepository.findById(1L);

        Member findMember1 = result1.get();
        System.out.println("before: " + findMember1);

        Optional<Member> result2 = memberRepository.findById(2L);

        Member findMember2 = result1.get();
        System.out.println("before: " + findMember2);

        Detail myDetail = Detail.builder()
                .habit(true)
                .smoking(false)
                .ear(Ear.BRIGHT)
                .sleep(Time.EARLY)
                .out(Out.OUT)
                .cleaning(Cleaning.CLEAN)
                .tendency(Tendency.ALONE)
                .member(member)
                .build();

        detailRepository.save(myDetail);

        Detail yourDetail = Detail.builder()
                .habit(false)
                .smoking(true)
                .ear(Ear.BRIGHT)
                .sleep(Time.EARLY)
                .out(Out.OUT)
                .cleaning(Cleaning.CLEAN)
                .tendency(Tendency.TOGETHER)
                .member(member1)
                .build();

        detailRepository.save(yourDetail);
        
        DetailDto userDetail = detailService.getUserDetailById(member.getId());
        DetailDto opponentDetail = detailService.getOpponentDetailById(member1.getId());
        
        List<DetailDto> Details = new ArrayList<>();

        Details.add(userDetail);
        Details.add(opponentDetail);

        for (DetailDto detail : Details) {
            System.out.println(detail);
        }
    }
}
