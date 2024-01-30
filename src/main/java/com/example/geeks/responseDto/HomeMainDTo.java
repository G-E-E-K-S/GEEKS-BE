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
    private List<PointAndMemberDTO> points = new ArrayList<>();

    private List<HomeRealTimePostDTO> livePosts = new ArrayList<>();

    private List<HomeRealTimePostDTO> weeklyPosts = new ArrayList<>();
}
