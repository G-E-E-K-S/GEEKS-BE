package com.example.geeks.domain;

import com.example.geeks.requestDto.ChatRoomDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "my_user_id")
    private Member user;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "opponent_user_id")
    private Member opponentUser;

    @OneToMany(mappedBy = "chatRoom", fetch = FetchType.EAGER) // FetchType.LAZY로 변경
    private List<ChatHistory> histories = new ArrayList<>();


    public ChatRoomDTO toDTO() {
        ChatRoomDTO dto = new ChatRoomDTO();
        dto.setId(this.id);
        dto.setRoomId(this.roomId);
        dto.setUser(this.user);
        dto.setOpponentUser(this.opponentUser);
        dto.setHistories(this.histories);
        return dto;
    }

}