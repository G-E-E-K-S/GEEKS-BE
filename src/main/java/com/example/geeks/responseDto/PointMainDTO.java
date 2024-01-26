package com.example.geeks.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class PointMainDTO {
    private boolean exist;

    private List<PointAndMemberDTO> points = new ArrayList<>();
}
