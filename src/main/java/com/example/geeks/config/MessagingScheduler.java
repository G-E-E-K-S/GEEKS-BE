package com.example.geeks.config;

import com.example.geeks.requestDto.ChatMessage;
import com.example.geeks.responseDto.SendChatMessage;
import com.example.geeks.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessagingScheduler {

    private SimpMessagingTemplate messagingTemplate;
    private final ChatService chatService;

    @Autowired
    public void setMessagingTemplate(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @KafkaListener(topics = KafkaConstants.KAFKA_TOPIC, groupId = "${kafka.group.id:${random.uuid}}")
    public void checkNotice(ChatMessage message){
        log.info("checkNotice call");
        try{
            //messagingTemplate.setMessageConverter(new StringMessageConverter());
            Long chatId = chatService.saveMessage(message);
            chatService.readChat(chatId);
            messagingTemplate.convertAndSend("/subscribe/notice" + message.getRoomid(), SendChatMessage.of(chatId, message));
        }catch(Exception ex){
            log.error(ex.getMessage());
        }
    }
}