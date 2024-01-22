package com.example.geeks.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@ToString(exclude = {"member", "friend"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Point extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "my_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "friend_id")
    private Member friend;

    private int point;

    public void setPoint(int point) {
        this.point = point;
    }

    @Builder
    public Point(Member member, Member friend, int point) {
        this.member = member;
        this.friend = friend;
        this.point = point;
    }
}
