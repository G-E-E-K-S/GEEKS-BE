package com.example.geeks.domain;

import com.example.geeks.Enum.DormitoryType;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@ToString(exclude = "password")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String nickname;

    private String email;

    private String password;

    private String major;

    private int studentID;

    private int gender;

    private int exp;

    private String image_url;

    private String introduction;

    @Enumerated(EnumType.STRING)
    private DormitoryType type;

    public void changeIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }
    @Builder
    public Member(String nickname, String email, String password, String major, int studentID, int gender, int exp, String image_url, String introduction, DormitoryType type) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.major = major;
        this.studentID = studentID;
        this.gender = gender;
        this.exp = exp;
        this.image_url = image_url;
        this.introduction = introduction;
        this.type = type;
    }
}
