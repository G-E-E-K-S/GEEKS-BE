package com.example.geeks.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.geeks.Security.Util;
import com.example.geeks.domain.AcceptRoomMate;
import com.example.geeks.domain.Member;
import com.example.geeks.domain.Withdrawal;
import com.example.geeks.repository.*;
import com.example.geeks.requestDto.LoginDTO;
import com.example.geeks.requestDto.ProfileEditDTO;
import com.example.geeks.requestDto.ReasonDTO;
import com.example.geeks.responseDto.InformationDTO;
import com.example.geeks.responseDto.MyPageDTO;
import com.example.geeks.responseDto.MyProfileDTO;
import com.example.geeks.responseDto.SearchMemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    @Value("${jwt.secret}")
    private String secretKey;

    private final WithdrawalRepository withdrawalRepository;
    private final MemberRepository memberRepository;

    private final AcceptRoomMateRepository acceptRoomMateRepository;

    private final PointRepository pointRepository;

    private final Util util;

    private final BCryptPasswordEncoder encoder;

    private final AmazonS3 amazonS3;


    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String  uploadToS3(MultipartFile file, String nickname, int count) throws IOException {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String current_date = now.format(dateTimeFormatter);

        String fileName = nickname + current_date + count++;

        ObjectMetadata metadata = new ObjectMetadata();

        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        amazonS3.putObject(bucket, fileName, file.getInputStream(), metadata);
        System.out.println("사진 URL" + amazonS3.getUrl(bucket, fileName));

        return fileName;
    }

    public boolean availableEmail(String email) {
        return memberRepository.findByEmail(email).isEmpty();
    }

    public boolean availableNickname(String nickname) {
        return memberRepository.findByNickname(nickname).isEmpty();
    }

    @Transactional
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Transactional
    public void editNickname(String nickname, Long id) {
        Optional<Member> result = memberRepository.findById(id);

        Member member = result.get();
        member.changeNickname(nickname);
    }

    @Transactional
    public void editIntroduction(String introduction, Long id) {
        Optional<Member> result = memberRepository.findById(id);

        Member member = result.get();
        member.changeIntroduction(introduction); // 변경감지
    }

    @Transactional
    public void editProfile(ProfileEditDTO dto, Long id, List<MultipartFile> files) throws IOException {
        Member member = memberRepository.findMemberFetchDetail(id)
                .orElseThrow(() -> new NotFoundException("Could not found id : " + id));

        if(files != null) {
            if(!member.getPhotoName().equals("")) {
                amazonS3.deleteObject(bucket, member.getPhotoName());
            }

            for (MultipartFile file : files) {
                String fileName = uploadToS3(file, member.getNickname(), 1);
                member.setPhotoName(fileName);
            }
        }

        if(!member.getType().equals(dto.getType())) {
            pointRepository.deletePointWhenChangeType(id);
        }

        member.changeProfile(dto);
    }

    public MyPageDTO sendMyPage(Long userId) {
        Member member = memberRepository.findMemberFetchJoinWithDetail(userId);
        System.out.println("member = " + member);

        return MyPageDTO.builder()
                .photoName(member.getPhotoName())
                .major(member.getMajor())
                .studentID(member.getStudentID())
                .introduction(member.getIntroduction())
                .nickname(member.getNickname())
                .type(member.getType())
                .exist(member.getDetail() != null ? true : false)
                .open(member.isOpen())
                .gender(member.getGender())
                .build();
    }

    public String createToken(Long id, String nickname){
        return util.createJwt(id, nickname, secretKey);
    }

    public Long findId(String nickname){
        return memberRepository.findIdByNickname(nickname);
    }

    public String login(LoginDTO loginDTO){
        List<Member> members = memberRepository.findByEmail(loginDTO.getEmail());
        // 1. Id가 틀린 경우
        if(members.isEmpty()) return "Email Not Found";

        // 2. Pw가 틀린 경우
        Member user = members.get(0);

        // 사용자가 입력한 비밀번호 (rawPassword)와 암호화된 비밀번호 (hashedPassword)를 비교
        if(!encoder.matches(loginDTO.getPassword(), user.getPassword())) return "Password Not Equal";

        String nickname = user.getNickname();
        Long id = user.getId();
        return Util.createJwt(id, nickname, secretKey);
    }

    @Transactional
    public void editPassword(String encodePassword, Long userId){
        Member member = memberRepository.findByIdFetchDetail(userId)
                .orElseThrow(() -> new NotFoundException("Could not found id : " + userId));

        member.changePassword(encodePassword);
    }

    public boolean checkPassword(Long userId, String password) {
        String encodePassword = memberRepository.findPassword(userId);

        if(!encoder.matches(password, encodePassword)) return false;

        return true;
    }

    @Transactional
    public void editOpen(Long userId, boolean open) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Could not found id : " + userId));

        member.setOpen(open);
    }

    public MyProfileDTO showProfile(Long userId) {
        Member member = memberRepository.findMemberFetchDetail(userId)
                .orElseThrow(() -> new NotFoundException("Could not found id : " + userId));

        Optional<AcceptRoomMate> acceptRoomMate = acceptRoomMateRepository.findAcceptRoomMateOptional(userId);

        MyProfileDTO dto;

        if(acceptRoomMate.isPresent()) {
            Member roommate;

            if(acceptRoomMate.get().getAccept().getId().equals(userId)) {
                roommate = acceptRoomMate.get().getSender();
            } else {
                roommate = acceptRoomMate.get().getAccept();
            }

            dto = MyProfileDTO.builder()
                    .myMajor(member.getMajor())
                    .myNickname(member.getNickname())
                    .myPhotoName(member.getPhotoName())
                    .myStudentID(member.getStudentID())
                    .type(roommate.getType())
                    .major(roommate.getMajor())
                    .nickname(roommate.getNickname())
                    .photoName(roommate.getPhotoName())
                    .studentID(roommate.getStudentID())
                    .build();
        } else {
            dto = MyProfileDTO.builder()
                    .myMajor(member.getMajor())
                    .myNickname(member.getNickname())
                    .myPhotoName(member.getPhotoName())
                    .myStudentID(member.getStudentID())
                    .build();
        }

        return dto;
    }

    public InformationDTO information(Long userId) {
        Member member = memberRepository.findMemberFetchDetail(userId)
                .orElseThrow(() -> new NotFoundException("Could not found id : " + userId));

        return new InformationDTO(member.getEmail(), member.getCreatedDate());
    }

    @Transactional
    public void deleteMember(Long id){
        pointRepository.deletePointWhenMemberWithDraw(id);
        memberRepository.deleteById(id);
    }

    @Transactional
    public void saveReason(ReasonDTO reasonDTO){
        Withdrawal withdrawal = new Withdrawal(reasonDTO.getReason(), reasonDTO.getDetailReason());
        withdrawalRepository.save(withdrawal);
    }

    public List<SearchMemberDTO> searchMember(Long userId, String keyword) {
        Member member1 = memberRepository.findMemberFetchDetail(userId)
                .orElseThrow(() -> new NotFoundException("Could not found id : " + userId));

        List<Member> members =
                memberRepository.findSearchMember(userId, "%" + keyword + "%", member1.getType(), member1.getGender());

        return members.stream().map(member ->
                new SearchMemberDTO(
                        member.getId(),
                        member.getNickname(),
                        member.getMajor())).toList();
    }
}
