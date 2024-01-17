package com.example.geeks.domain;

import com.example.geeks.Enum.*;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Detail extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detail_id")
    private Long id;

    private boolean smoking;

    private boolean habit;

    @Enumerated(EnumType.STRING)
    private Ear ear;

    @Enumerated(EnumType.STRING)
    private Time sleeping;

    @Enumerated(EnumType.STRING)
    private Time wakeup;

    @Enumerated(EnumType.STRING)
    private Out outing;

    @Enumerated(EnumType.STRING)
    private Cleaning cleaning;

    @Enumerated(EnumType.STRING)
    private Tendency tendency;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Detail(boolean smoking, boolean habit, Ear ear, Time sleep, Time wakeup, Out out, Cleaning cleaning, Tendency tendency, Member member) {
        this.smoking = smoking;
        this.habit = habit;
        this.ear = ear;
        this.sleeping = sleep;
        this.wakeup = wakeup;
        this.outing = out;
        this.cleaning = cleaning;
        this.tendency = tendency;
        this.member = member;
    }
}
