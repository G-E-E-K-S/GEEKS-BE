package com.example.geeks.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Point extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "my_id")
    private Member member;

    private Long friend_id;

    private int point;

    @Builder
    public Point(Member member, Long friend_id, int point) {
        this.member = member;
        this.friend_id = friend_id;
        this.point = point;
    }
}
