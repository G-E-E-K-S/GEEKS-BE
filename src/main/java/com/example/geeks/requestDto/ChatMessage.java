package com.example.geeks.requestDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class ChatMessage {
    private String roomid; //방번호
    private String user; //메세지 보낸사람
    private String content; //메세지
    private String timeStamp; // 시간
}
