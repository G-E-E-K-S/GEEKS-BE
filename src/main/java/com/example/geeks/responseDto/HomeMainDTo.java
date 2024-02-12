package com.example.geeks.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class HomeMainDTo {

    private String nickname;
    private boolean exist;

    private boolean roommateApply;

    private List<PointAndMemberDTO> points;

    private List<HomeRealTimePostDTO> livePosts;

    private List<HomeRealTimePostDTO> weeklyPost;
}
