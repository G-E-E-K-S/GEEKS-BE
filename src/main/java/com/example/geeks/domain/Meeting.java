package com.example.geeks.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REMOVE;


@Getter
@Setter
@Entity
@NoArgsConstructor
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomId;

    private String name;

    @ElementCollection
    private List<String> userNickname;


    public Meeting(String roomId, String name) {
        this.roomId = roomId;
        this.name = name;
    }

    @OneToMany(mappedBy = "Meeting", fetch = FetchType.LAZY, cascade = {PERSIST, REMOVE})
    private List<MeetingHistory> histories = new ArrayList<>();
    private List<ChatHistory> histories = new ArrayList<>();

}
