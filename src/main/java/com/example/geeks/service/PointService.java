package com.example.geeks.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.example.geeks.domain.Detail;
import com.example.geeks.domain.Member;
import com.example.geeks.domain.Point;
import com.example.geeks.domain.SaveRoomMate;
import com.example.geeks.repository.DetailRepository;
import com.example.geeks.repository.MemberRepository;
import com.example.geeks.repository.PointRepository;
import com.example.geeks.repository.SaveRoomMateRepository;
import com.example.geeks.responseDto.PointAndMemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PointService {
    private final PointRepository pointRepository;

    private final DetailRepository detailRepository;

    private final MemberRepository memberRepository;

    private final SaveRoomMateRepository saveRoomMateRepository;

    public List<PointAndMemberDTO> allPoint(Long userId) {
        List<Point> points = pointRepository.findFetchMember(userId);

        return points.stream()
                .map(point ->
                        new PointAndMemberDTO(
                                point.getFriend().getId(),
                                point.getFriend().getNickname(),
                                point.getFriend().getMajor(),
                                point.getFriend().getIntroduction(),
                                point.getFriend().getPhotoName(),
                                point.getFriend().getStudentID(),
                                point.getPoint(), point.getFriend().getDetail().isSmoking())).toList();
    }

    @Transactional
    public boolean calculate(Long userId) {
        Member member = memberRepository.findByIdFetchDetail(userId)
                .orElseThrow(() -> new NotFoundException("Could not found id : " + userId));

        Detail myDetail = member.getDetail();

        if(myDetail == null) {
            return false;
        }

        List<Long> friendIds = new ArrayList<>();
        friendIds.add(userId);

        List<Point> points = pointRepository.findByMemberIdFetch(userId);

        for (Point point : points) {
            friendIds.add(point.getFriend().getId());

            if(point.getLastModifiedDate().isAfter(myDetail.getLastModifiedDate()) ||
                    point.getLastModifiedDate().isAfter(point.getFriend().getLastModifiedDate())) {
                point.setPoint(pointCalculator(myDetail, point.getFriend().getDetail()));
            }
        }

        List<Detail> detailList = detailRepository.findListNotInFriendId(friendIds, member.getGender(), member.getType());

        for (Detail detail : detailList) {
            Point result = Point.builder()
                    .member(myDetail.getMember())
                    .friend(detail.getMember())
                    .point(pointCalculator(myDetail, detail))
                    .build();

            pointRepository.save(result);
        }

        return true;
    }

    @Transactional
    public List<PointAndMemberDTO> getSaveRoomMateList(Long userId){
        calculate(userId); // 계산 다시

        List<SaveRoomMate> saveRoomMates = saveRoomMateRepository.findAllByIdFetch(userId);

        List<Long> friendIds = new ArrayList<>();

        for(SaveRoomMate saveRoomMate : saveRoomMates){
            friendIds.add(saveRoomMate.getYou().getId());
        }

        List<Point> points = pointRepository.findByFriendIdInListFetch(friendIds, userId);

        return points.stream()
                .map(point ->
                        new PointAndMemberDTO(
                                point.getFriend().getId(),
                                point.getFriend().getNickname(),
                                point.getFriend().getMajor(),
                                point.getFriend().getIntroduction(),
                                point.getFriend().getPhotoName(),
                                point.getFriend().getStudentID(),
                                point.getPoint(), point.getFriend().getDetail().isSmoking())).toList();
    }

    @Transactional
    public List<PointAndMemberDTO> homePointList(Long userId) {
        Member member = memberRepository.findByIdFetchDetail(userId)
                .orElseThrow(() -> new NotFoundException("Could not found id : " + userId));

        Detail myDetail = member.getDetail();

        List<Long> friendIds = new ArrayList<>();
        friendIds.add(userId);

        PageRequest pageRequest =
                PageRequest.of(0, 3);

        List<Point> points = pointRepository.findByMemberIdFetchToHome(userId, pageRequest);

        for (Point point : points) {
            friendIds.add(point.getFriend().getId());

            if(point.getLastModifiedDate().isAfter(myDetail.getLastModifiedDate()) ||
                    point.getLastModifiedDate().isAfter(point.getFriend().getLastModifiedDate())) {
                point.setPoint(pointCalculator(myDetail, point.getFriend().getDetail()));
            }
        }

        return points.stream()
                .map(point ->
                        new PointAndMemberDTO(
                                point.getFriend().getId(),
                                point.getFriend().getNickname(),
                                point.getFriend().getMajor(),
                                point.getFriend().getIntroduction(),
                                point.getFriend().getPhotoName(),
                                point.getFriend().getStudentID(),
                                point.getPoint(), point.getFriend().getDetail().isSmoking())).toList();
    }

    public int pointCalculator(Detail myDetail, Detail detail) {
        int count = 0, point = 0;

        if(detail.isHabit() == myDetail.isHabit()) count++;
        if(detail.getEar().equals(myDetail.getEar())) count++;
        if(detail.isSmoking() == myDetail.isSmoking()) count++;
        if(detail.getCleaning().equals(myDetail.getCleaning())) count++;
        if(detail.getOuting().equals(myDetail.getOuting())) count++;
        if(detail.getTendency().equals(myDetail.getTendency())) count++;
        if(detail.getWakeup().equals(myDetail.getWakeup())) count++;
        if(detail.getSleeping().equals(myDetail.getSleeping())) count++;

        switch (count){
            case 1:
                point = 10;
                break;
            case 2:
                point = 20;
                break;
            case 3:
                point = 30;
                break;
            case 4:
                point = 40;
                break;
            case 5:
                point = 55;
                break;
            case 6:
                point = 70;
                break;
            case 7:
                point = 85;
                break;
            case 8:
                point = 100;
                break;
        }

        return point;
    }
}
