package com.example.geeks.Init;

import com.example.geeks.Enum.DormitoryType;
import com.example.geeks.Enum.Gender;
import com.example.geeks.domain.Member;
import com.example.geeks.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class MemberInit {
    private final MemberRepository memberRepository;

    @PostConstruct
    public void memberInit() {
        Member member1 = Member.builder()
                .nickname("member1")
                .email("m1@test.com")
                .password("1234")
                .major("소프트웨어")
                .studentID(19)
                .gender(Gender.MALE)
                .exp(2)
                .image_url("")
                .introduction("")
                .type(DormitoryType.NEW)
                .build();

        Member member2 = Member.builder()
                .nickname("member2")
                .email("m2@test.com")
                .password("1234")
                .major("소프트웨어")
                .studentID(17)
                .gender(Gender.MALE)
                .exp(3)
                .image_url("")
                .introduction("")
                .type(DormitoryType.NEW)
                .build();

        Member member3 = Member.builder()
                .nickname("member3")
                .email("m3@test.com")
                .password("1234")
                .major("소프트웨어")
                .studentID(22)
                .gender(Gender.FEMALE)
                .exp(0)
                .image_url("")
                .introduction("")
                .type(DormitoryType.OLD)
                .build();

        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
    }
}
