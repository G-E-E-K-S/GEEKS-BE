package com.example.geeks.service;

import com.example.geeks.domain.Point;
import com.example.geeks.repository.PointRepository;
import com.example.geeks.responseDto.PointAndMemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PointService {
    private final PointRepository pointRepository;

    public List<PointAndMemberDTO> allPoint(Long userId) {
        List<Point> points = pointRepository.findFetchMember(userId);

        return points.stream()
                .map(point ->
                        new PointAndMemberDTO(
                                point.getFriend().getId(),
                                point.getFriend().getNickname(),
                                point.getFriend().getMajor(),
                                point.getFriend().getIntroduction(),
                                point.getFriend().getStudentID(),
                                point.getPoint())).toList();
    }
}
