package com.example.geeks.domain;

import com.example.geeks.requestDto.ChatRoomDTO;
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
public class ChatRoom{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public ChatRoom(String roomId, Member user, Member opponentUser) {
        this.roomId = roomId;
        this.user = user;
        this.opponentUser = opponentUser;
    }

    private String roomId;

    @ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "my_user_id")
    private Member user;

    @ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "opponent_user_id")
    private Member opponentUser;

    @OneToMany(mappedBy = "chatRoom", fetch = FetchType.LAZY, cascade = {PERSIST, REMOVE})
    private List<ChatHistory> histories = new ArrayList<>();
}