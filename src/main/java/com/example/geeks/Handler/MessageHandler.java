package com.example.geeks.Handler;

import com.example.geeks.config.KafkaConstants;
import com.example.geeks.requestDto.ChatMessage;
import com.example.geeks.requestDto.ChatRoomDTO;
import com.example.geeks.service.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Slf4j
@Controller
public class MessageHandler {

    @Autowired
    private KafkaTemplate<String, ChatMessage> kafkaTemplate;

    @Autowired
    private ChatService chatService;

    @Autowired
    private ChatRoomDTO chatRoomDTO;
    
    @MessageMapping("/message")
    @SendTo("/topic/greetings")
    public void greeting(ChatMessage message) throws Exception {
        log.info("message received, message:{}", message.toString());
        // RDS에 데이터 입력
        chatService.saveMessage(message);
        // 정상적으로 데이터가 입력된 경우
        // 카프카에 메세지를 push
        kafkaTemplate.send(KafkaConstants.KAFKA_TOPIC, message).get();
    }
}