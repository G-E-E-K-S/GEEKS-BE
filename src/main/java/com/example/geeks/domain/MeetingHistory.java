package com.example.geeks.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
public class MeetingHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member sender;

    private String message;

    private int readCount = 2;

    private LocalDateTime createdAt;
    public MeetingHistory(Meeting meeting, Member sender, String message, LocalDateTime createdAt) {
        this.meeting = meeting;
        this.sender = sender;
        this.message = message;
        this.createdAt = createdAt;
    }
    public static MeetingHistory create(Meeting meeting, Member sender, String message, LocalDateTime createdAt) {
        MeetingHistory meetingHistory = new MeetingHistory(meeting, sender, message, createdAt);
        meeting.getHistories().add(meetingHistory);
        return meetingHistory;
    }
}