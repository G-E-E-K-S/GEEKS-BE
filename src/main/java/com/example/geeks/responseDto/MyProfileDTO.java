package com.example.geeks.responseDto;

import com.example.geeks.Enum.DormitoryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MyProfileDTO {

    private Long roommateId;

    private String myNickname;

    private String nickname;

    private String myMajor;

    private String major;

    private String myPhotoName;

    private String photoName;

    private int myStudentID;

    private int studentID;

    private DormitoryType type;

    public MyProfileDTO(String myNickname, String myMajor, String myPhotoName, int myStudentID) {
        this.myNickname = myNickname;
        this.myMajor = myMajor;
        this.myPhotoName = myPhotoName;
        this.myStudentID = myStudentID;
    }
}
