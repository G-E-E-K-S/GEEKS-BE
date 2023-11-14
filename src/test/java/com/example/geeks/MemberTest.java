package com.example.geeks;

import com.example.geeks.Enum.DormitoryType;
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
    public void testEditIntroduction() {
        Member member = Member.builder()
                .nickname("admin")
                .email("bak3839@naver.com")
                .password("1234")
                .major("소프트웨어")
                .gender(1)
                .exp(3)
                .type(DormitoryType.NEW)
                .image_url("basic")
                .introduction("")
                .build();

        memberRepository.save(member);

        memberService.editIntroduction("hi", 1L);

        Optional<Member> result = memberRepository.findById(1L);

        Member findMember = result.get();
        System.out.println(findMember);
    }
}
