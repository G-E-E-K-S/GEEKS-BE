package com.example.geeks.Init;

import com.example.geeks.Enum.*;
import com.example.geeks.domain.Detail;
import com.example.geeks.domain.Member;
import com.example.geeks.repository.DetailRepository;
import com.example.geeks.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
@RequiredArgsConstructor
public class MemberInit {
    private final MemberRepository memberRepository;

    private final DetailRepository detailRepository;

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

        Detail detail1 = Detail.builder()
                .habit(true)
                .smoking(false)
                .ear(Ear.BRIGHT)
                .sleep(Time.EARLY)
                .wakeup(Time.RANDOM)
                .out(Out.OUT)
                .cleaning(Cleaning.CLEAN)
                .tendency(Tendency.ALONE)
                .member(member1)
                .build();

        Detail detail2 = Detail.builder()
                .habit(false)
                .smoking(true)
                .ear(Ear.BRIGHT)
                .sleep(Time.EARLY)
                .wakeup(Time.EARLY)
                .out(Out.OUT)
                .cleaning(Cleaning.CLEAN)
                .tendency(Tendency.TOGETHER)
                .member(member2)
                .build();

        Detail detail3 = Detail.builder()
                .habit(false)
                .smoking(true)
                .ear(Ear.BRIGHT)
                .sleep(Time.EARLY)
                .wakeup(Time.EARLY)
                .out(Out.OUT)
                .cleaning(Cleaning.CLEAN)
                .tendency(Tendency.TOGETHER)
                .member(member3)
                .build();

        detailRepository.save(detail1);
        detailRepository.save(detail2);
        detailRepository.save(detail3);
    }
}
