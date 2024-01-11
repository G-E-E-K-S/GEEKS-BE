package com.example.geeks.service;

import com.example.geeks.domain.ChatRoom;
import com.example.geeks.domain.Member;
import com.example.geeks.repository.ChatRoomRepository;
import com.example.geeks.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public String createChatRoom(String myNickName, String yourNickName) {
        Member user = getUserByNickname(myNickName); //User에서 찾아야 하는거고
        Member opponentUser = getUserByNickname(yourNickName); //Coordinator에서 찾아야 하는거고

        System.out.println(user.getNickname());
        System.out.println(opponentUser.getNickname());

        ChatRoom chatRoom = findChatRoom(user, opponentUser);

        if(chatRoom == null){
            chatRoom = ChatRoom.create(user ,opponentUser);
            chatRoomRepository.save(chatRoom);
        }
        return chatRoom.getRoomId();
    }

    private ChatRoom findChatRoom(Member user, Member opponentUser) {
        return chatRoomRepository.findByMembersIn(user, opponentUser).get();
    }

    public Member getUserByNickname(String name) {
        List<Member> members = memberRepository.findByNickname(name);
        return members.isEmpty() ? null : members.get(0);
    }
}
