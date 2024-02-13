package com.example.geeks.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class DetailCompareDTO {
    private String nickname;

    private String major;

    private String photoName;

    private String introduction;

    private boolean roommateApply; // 룸메이트 신청을 보냈는지 여부

    private boolean roommateState; // 서로 룸메이트인지 여부

    private boolean acceptRoommate; // 현재 매칭된 룸메이트가 있는지 여부

    private int studentID;

    private int point;

    List<DetailDTO> details;
}
