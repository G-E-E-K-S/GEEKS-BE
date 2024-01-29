package com.example.geeks.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CommunityHistoryDTO {

    private List<PostHistoryDTO> postHistories;
    private List<CommentHistoryDTO> commentHistories;
}
