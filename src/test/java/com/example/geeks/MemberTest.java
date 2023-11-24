package com.example.geeks;

import com.example.geeks.Enum.DormitoryType;
import com.example.geeks.Enum.Gender;
import com.example.geeks.domain.Member;
import com.example.geeks.repository.MemberRepository;
import com.example.geeks.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
@Transactional
@Rollback(false)
public class MemberTest {
    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;


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
}
