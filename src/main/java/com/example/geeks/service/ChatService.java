package com.example.geeks.service;

import com.example.geeks.domain.ChatHistory;
import com.example.geeks.domain.ChatRoom;
import com.example.geeks.domain.Member;
import com.example.geeks.repository.ChatHistoryRepository;
import com.example.geeks.repository.ChatRoomRepository;
import com.example.geeks.repository.MemberRepository;
import com.example.geeks.requestDto.ChatMessage;
import com.example.geeks.requestDto.ChatRoomDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;
    private final ChatHistoryRepository chatHistoryRepository;

    @Transactional
    public String createChatRoom(String myNickName, String yourNickName) {
        Member user = getUserByNickname(myNickName); //User에서 찾아야 하는거고
        Member opponentUser = getUserByNickname(yourNickName); //Coordinator에서 찾아야 하는거고

        System.out.println(user.getNickname());
        System.out.println(opponentUser.getNickname());

        ChatRoom chatRoom = findChatRoom(user, opponentUser);

        if(chatRoom == null){
            chatRoom = create(user ,opponentUser);
            chatRoomRepository.save(chatRoom);
        }
        return chatRoom.getRoomId();
    }

    @Transactional
    public void saveMessage(ChatMessage message) {
        ChatRoom chatRoom = chatRoomRepository.findById(message.getRoomid());
        Member sender = getUserByNickname(message.getUser());
        String _message = message.getContent();
        saveChatMessage(chatRoom, sender, _message, message.getTimeStamp());
    }

    @Transactional
    public void saveChatMessage(ChatRoom chatRoom, Member sender, String message, String createdAt) {
        ChatHistory chatHistory;
        chatHistory = ChatHistory.create(chatRoom, sender, message, createdAt);
        chatHistoryRepository.save(chatHistory);
    }

    public ChatRoomDTO getMessages(String roomUUID) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomUUID);
        ChatRoomDTO chatRoomDTO = new ChatRoomDTO(chatRoom.getRoomId(), chatRoom.getUser(), chatRoom.getOpponentUser(), chatRoom.getHistories());
        return chatRoomDTO;
    }

    public ChatRoom create(Member user, Member opponentUser) {
        ChatRoom chatRoom = new ChatRoom(UUID.randomUUID().toString(), user, opponentUser);
        return chatRoom;
    }


    private ChatRoom findChatRoom(Member user, Member opponentUser) {
        return chatRoomRepository.findByUserAndOpponentUser(user, opponentUser).get();
    }

    public Member getUserByNickname(String name) {
        List<Member> members = memberRepository.findByNickname(name);
        return members.isEmpty() ? null : members.get(0);
    }

    public void readChat(Long chatid){
        ChatHistory chatHistory = chatHistoryRepository.findById(chatid).get();
        if(chatHistory != null){
            chatHistory.setReadCount(chatHistory.getReadCount() - 1);
        }
    }

    public List<ChatRoomDTO> getChatingRooms(String nickname){
        Member user = getUserByNickname(nickname);
        List<ChatRoom> chatRoom = chatRoomRepository.findByUserOrOpponentUser(user);

        List<ChatRoomDTO> chatRoomDTOs = chatRoom.stream()
                .map(ChatRoom::toDTO)
                .collect(Collectors.toList());
        return chatRoomDTOs;
    }

    public List<ChatRoom> getAllRooms(){
        return chatRoomRepository.findAll();
    }

    public ChatRoom findRoom(String RoomId){
        return chatRoomRepository.findById(RoomId);
    }

}
