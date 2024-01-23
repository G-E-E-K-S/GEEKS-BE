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
                .introduction("외출이 잦아요! 기숙사는 가끔 들어와요")
                .type(DormitoryType.NEW)
                .build();

        Member member2 = Member.builder()
                .nickname("member2")
                .email("m2@test.com")
                .password("1234")
                .major("소프트웨어")
                .studentID(17)
                .gender(Gender.FEMALE)
                .exp(3)
                .image_url("")
                .introduction("예민하지 않으신 분들 찾아요")
                .type(DormitoryType.NEW)
                .build();

        Member member3 = Member.builder()
                .nickname("눈누난나")
                .email("m3@test.com")
                .password("1234")
                .major("소프트웨어")
                .studentID(22)
                .gender(Gender.FEMALE)
                .exp(0)
                .image_url("")
                .introduction("같은 과이신 분들 선호합니다!")
                .type(DormitoryType.OLD)
                .build();

        Member member4 = Member.builder()
                .nickname("종강하고싶다")
                .email("m3@test.com")
                .password("1234")
                .major("전자공학과")
                .studentID(22)
                .gender(Gender.MALE)
                .exp(0)
                .image_url("")
                .introduction("같은 과이신 분들 선호합니다!")
                .type(DormitoryType.NEW)
                .build();

        Member member5 = Member.builder()
                .nickname("멋쟁이 토마토")
                .email("m3@test.com")
                .password("1234")
                .major("인더스트리얼디자인")
                .studentID(21)
                .gender(Gender.FEMALE)
                .exp(3)
                .image_url("")
                .introduction("외출 잦은 사람 선호합니다!")
                .type(DormitoryType.NEW)
                .build();

        Member member6 = Member.builder()
                .nickname("수뭉이")
                .email("m3@test.com")
                .password("1234")
                .major("디지털만화영상")
                .studentID(21)
                .gender(Gender.FEMALE)
                .exp(3)
                .image_url("")
                .introduction("외출 잦은 사람 선호합니다!")
                .type(DormitoryType.NEW)
                .build();

        Member member7 = Member.builder()
                .nickname("피자먹고싶다")
                .email("m3@test.com")
                .password("1234")
                .major("한국언어문화")
                .studentID(20)
                .gender(Gender.FEMALE)
                .exp(3)
                .image_url("")
                .introduction("외출 잦은 사람 선호합니다!")
                .type(DormitoryType.NEW)
                .build();

        Member member8 = Member.builder()
                .nickname("슴우D")
                .email("m3@test.com")
                .password("1234")
                .major("커뮤니케이션디자인")
                .studentID(19)
                .gender(Gender.MALE)
                .exp(3)
                .image_url("")
                .introduction("외출 잦은 사람 선호합니다!")
                .type(DormitoryType.NEW)
                .build();

        Member member9 = Member.builder()
                .nickname("너무추워")
                .email("m3@test.com")
                .password("1234")
                .major("문화예술경영")
                .studentID(18)
                .gender(Gender.MALE)
                .exp(0)
                .image_url("")
                .introduction("외출 잦은 사람 선호합니다!")
                .type(DormitoryType.NEW)
                .build();

        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
        memberRepository.save(member4);
        memberRepository.save(member5);
        memberRepository.save(member6);
        memberRepository.save(member7);
        memberRepository.save(member8);
        memberRepository.save(member9);

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

        Detail detail4 = Detail.builder()
                .habit(true)
                .smoking(false)
                .ear(Ear.DARK)
                .sleep(Time.EARLY)
                .wakeup(Time.RANDOM)
                .out(Out.OUT)
                .cleaning(Cleaning.CLEAN)
                .tendency(Tendency.ALONE)
                .member(member4)
                .build();

        Detail detail5 = Detail.builder()
                .habit(true)
                .smoking(false)
                .ear(Ear.BRIGHT)
                .sleep(Time.EARLY)
                .wakeup(Time.RANDOM)
                .out(Out.HOME)
                .cleaning(Cleaning.CLEAN)
                .tendency(Tendency.TOGETHER)
                .member(member5)
                .build();

        Detail detail6 = Detail.builder()
                .habit(false)
                .smoking(false)
                .ear(Ear.BRIGHT)
                .sleep(Time.EARLY)
                .wakeup(Time.RANDOM)
                .out(Out.OUT)
                .cleaning(Cleaning.DIRTY)
                .tendency(Tendency.TOGETHER)
                .member(member6)
                .build();

        Detail detail7 = Detail.builder()
                .habit(false)
                .smoking(true)
                .ear(Ear.DARK)
                .sleep(Time.LATE)
                .wakeup(Time.LATE)
                .out(Out.HOME)
                .cleaning(Cleaning.CLEAN)
                .tendency(Tendency.ALONE)
                .member(member7)
                .build();

        Detail detail8 = Detail.builder()
                .habit(true)
                .smoking(false)
                .ear(Ear.DARK)
                .sleep(Time.LATE)
                .wakeup(Time.LATE)
                .out(Out.OUT)
                .cleaning(Cleaning.CLEAN)
                .tendency(Tendency.ALONE)
                .member(member8)
                .build();

        Detail detail9 = Detail.builder()
                .habit(true)
                .smoking(false)
                .ear(Ear.BRIGHT)
                .sleep(Time.EARLY)
                .wakeup(Time.RANDOM)
                .out(Out.PROMISE)
                .cleaning(Cleaning.OPPONENT)
                .tendency(Tendency.OPPONENT)
                .member(member9)
                .build();

        detailRepository.save(detail1);
        detailRepository.save(detail2);
        detailRepository.save(detail3);
        detailRepository.save(detail4);
        detailRepository.save(detail5);
        detailRepository.save(detail6);
        detailRepository.save(detail7);
        detailRepository.save(detail8);
        detailRepository.save(detail9);
    }
}
