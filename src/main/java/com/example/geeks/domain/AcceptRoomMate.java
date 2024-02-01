package com.example.geeks.domain;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AcceptRoomMate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_mate_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    private Member accept;

    @ManyToOne(fetch = LAZY)
    private Member sender;

    public AcceptRoomMate(Member accept, Member sender) {
        this.accept = accept;
        this.sender = sender;
    }
}
