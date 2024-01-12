package com.example.geeks.Handler;

import com.example.geeks.config.KafkaConstants;
import com.example.geeks.requestDto.ChatMessage;
import com.example.geeks.requestDto.ChatRoomDTO;
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
    private ChatRoomDTO chatRoomDTO;

    @Transactional
    @MessageMapping("/message")
    @SendTo("/topic/greetings")
    public void greeting(ChatMessage message) throws Exception {
        log.info("message received, message:{}", message.toString());
        // 지금 시간을 넣어서 발송
        message.setTimeStamp(LocalDateTime.now().toString());
        // RDS에 데이터 입력
        int insert = chatRoomDTO.insertChatting(message);
        // 정상적으로 데이터가 입력된 경우
        if(insert > 0) {
            // 카프카에 메세지를 push
            kafkaTemplate.send(KafkaConstants.KAFKA_TOPIC, message).get();
        }
    }

}