package com.example.geeks.service;

import com.example.geeks.domain.ChatHistory;
import com.example.geeks.domain.ChatRoom;
import com.example.geeks.domain.Member;
import com.example.geeks.repository.ChatHistoryRepository;
import com.example.geeks.repository.ChatRoomRepository;
import com.example.geeks.repository.MemberRepository;
import com.example.geeks.requestDto.ChatMessage;
import com.example.geeks.requestDto.ChatRoomDTO;
import com.example.geeks.responseDto.ChatHistoryResponse;
import com.example.geeks.responseDto.ChatRoomDetailDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

        Optional<ChatRoom> chatRoom = findChatRoom(user, opponentUser);
        ChatRoom result;

        if(!chatRoom.isPresent()){
            result = create(user ,opponentUser);
            chatRoomRepository.save(result);
        } else {
            result = chatRoom.get();
        }

        return result.getRoomId();
    }

    public Long saveMessage(ChatMessage message) {
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(message.getRoomid());
        Member sender = getUserByNickname(message.getUser());
        String _message = message.getContent();
        return saveChatMessage(chatRoom, sender ,_message, message.getCreateAt());
    }
    @Transactional
    public Long saveChatMessage(ChatRoom chatRoom, Member sender, String message, LocalDateTime createdAt) {
        ChatHistory chatHistory = ChatHistory.create(chatRoom, sender, message, createdAt);
        chatHistoryRepository.save(chatHistory);
        // 준형
        Long id = chatHistory.getId();
        return id;
    }
    @Transactional
    public ChatRoom create(Member user, Member opponentUser) {
        ChatRoom chatRoom = new ChatRoom(UUID.randomUUID().toString(), user, opponentUser);
        return chatRoom;
    }


    private Optional<ChatRoom> findChatRoom(Member user, Member opponentUser) {
        return chatRoomRepository.findByUserAndOpponentUser(user, opponentUser);
    }

    public Member getUserByNickname(String name) {
        List<Member> members = memberRepository.findByNickname(name);
        return members.isEmpty() ? null : members.get(0);
    }

    @Transactional
    public void readChat(Long chatid){
        ChatHistory chatHistory = chatHistoryRepository.findById(chatid).get();
        if(chatHistory != null){
            System.out.println("chatHistory = " + chatHistory);
            chatHistory.setReadCount(chatHistory.getReadCount() - 1);
        }
    }

    public List<ChatRoomDTO> getChatingRooms(String nickname){
        Member user = getUserByNickname(nickname);
        List<ChatRoom> chatRoom = chatRoomRepository.findByUserOrOpponentUser(user.getId());

        List<ChatRoomDTO> chatRoomDTOs = chatRoom
                .stream()
                .map(chatRoom1 -> new ChatRoomDTO(
                        chatRoom1.getRoomId(),
                        user.getNickname(),
                        chatRoom1.getUser().getNickname().equals(nickname)  ?
                                chatRoom1.getOpponentUser().getNickname() : chatRoom1.getUser().getNickname() ,
                        chatRoom1.getHistories().stream().map(
                                chatHistory -> new ChatHistoryResponse(
                                        chatHistory.getSender().getNickname(),
                                        chatHistory.getReadCount(),
                                        chatHistory.getMessage(),
                                        chatHistory.getCreatedAt())).toList())).toList();
        return chatRoomDTOs;
    }
    public List<ChatRoom> getAllRooms(){
        return chatRoomRepository.findAll();
    }

    public ChatRoomDetailDTO findRoom(String roomId, String nickname, Long userId){
        // 채팅방 들어갈 때 readCount 벌크 연산
        int count = chatHistoryRepository.bulkReadCount(userId);

        ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId);

        Member user = chatRoom.getUser();
        Member opponentUser = !user.getNickname().equals(nickname) ? user : chatRoom.getOpponentUser();

        return new ChatRoomDetailDTO(opponentUser.getId(), chatRoom.getRoomId(),
                nickname,
                opponentUser.getNickname(),
                opponentUser.getMajor(),
                opponentUser.getStudentID(),
                chatRoom.getHistories()
                        .stream()
                        .map(chatHistory ->
                                new ChatHistoryResponse(
                                        chatHistory.getSender().getNickname(),
                                        chatHistory.getReadCount(),
                                        chatHistory.getMessage(),
                                        chatHistory.getCreatedAt())).toList());

    }

    @Transactional
    public void deleteHistoryAndChatRoom(Long id){
        List<ChatRoom> chatRooms = chatRoomRepository.findByUserOrOpponentUser(id);
        for (ChatRoom chatRoom : chatRooms) {
            chatHistoryRepository.deleteChatHistoriesByChatRoom_Id(chatRoom.getId());
            chatRoomRepository.delete(chatRoom);
        }
    }

    @Transactional
    public void deleteChatRoom(String id){
        chatRoomRepository.deleteByRoomId(id);
    }
}
