package com.example.geeks.repository;

import com.example.geeks.responseDto.ChatRoomDetailDTO;

public interface ChatRoomRepositoryCustom {
    ChatRoomDetailDTO findDetailDtoByRoomId();
}
