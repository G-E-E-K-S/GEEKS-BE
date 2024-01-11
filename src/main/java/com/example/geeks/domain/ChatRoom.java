package com.example.geeks.domain;

import com.example.geeks.requestDto.ChatRoomDTO;
import com.example.geeks.service.MemberService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.UUID;
@Getter
@Setter
@Entity
@NoArgsConstructor
public class ChatRoom{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "my_user_id")
    private Member user;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "opponent_user_id")
    private Member opponentUser;

    //@OneToMany(mappedBy = "chatRoom", fetch = FetchType.EAGER) // FetchType.LAZY로 변경
    //private List<ChatHistory> histories = new ArrayList<>();


    public static ChatRoom create(Member user, Member opponentUser) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId = UUID.randomUUID().toString();
        chatRoom.user = user;
        chatRoom.opponentUser = opponentUser;
        return chatRoom;
    }
    @Builder
    public ChatRoom(Long id, String roomId, Member user, Member opponentUser, /*List<ChatHistory> histories*/){
        this.id = id;
        this.roomId = roomId;
        this.user = user;
        this.opponentUser = opponentUser;
        //this.histories = histories;
    }

    public ChatRoomDTO toDTO() {
        ChatRoomDTO dto = new ChatRoomDTO();
        dto.setId(this.id);
        dto.setRoomId(this.roomId);
        dto.setUser(this.user);
        dto.setOpponentUser(this.opponentUser);
        //dto.setHistories(this.histories);
        return dto;
    }
}