package com.example.geeks.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomMate extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_mate_id")
    private Long id;

    @ManyToOne
    private Member sent;

    @ManyToOne
    private Member received;

    public RoomMate(Member sent, Member received) {
        this.sent = sent;
        this.received = received;
    }
}