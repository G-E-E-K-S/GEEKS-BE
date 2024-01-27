package com.example.geeks.domain;

import com.example.geeks.Enum.DormitoryType;
import com.example.geeks.Enum.Gender;
import com.example.geeks.requestDto.ProfileEditDTO;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;

@Entity
@Getter
@ToString(exclude = {"password", "posts", "point", "comments", "detail", "hearts", "scraps"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String nickname;

    private String email;

    private String password;

    private String major;

    private int studentID;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private int exp;

    private String photoName;

    private String introduction;

    private boolean open;

    @Enumerated(EnumType.STRING)
    private DormitoryType type;



    @OneToOne(mappedBy = "member", fetch = LAZY, cascade = {PERSIST, REMOVE})
    @JoinColumn(name = "detail_id")
    private Detail detail;

    @OneToMany(mappedBy = "member", cascade = {PERSIST, REMOVE}, orphanRemoval = true)
    private List<Point> point = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = {PERSIST, REMOVE}, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = {PERSIST, REMOVE})
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = {PERSIST, REMOVE}, orphanRemoval = true)
    private List<Heart> hearts = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = {PERSIST, REMOVE}, orphanRemoval = true)
    private List<PostScrap> scraps = new ArrayList<>();

    public void changeIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void changePassword(String password){
        this.password = password;
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    public void addPost(Post post) {
        this.posts.add(post);
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    public void addHeart(Heart heart) {
        this.hearts.add(heart);
    }

    public void addScrap(PostScrap postScrap) {
        this.scraps.add(postScrap);
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public void changeProfile(ProfileEditDTO dto) {
        this.type = dto.getType();
        this.nickname = dto.getNickname();
        this.major = dto.getMajor();
        this.studentID = dto.getStudentID();
        this.introduction = dto.getIntroduction();
    }

    @Builder
    public Member(String nickname, String email, String password, String major, int studentID, Gender gender, int exp, String image_url, String introduction, boolean open, DormitoryType type) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.major = major;
        this.studentID = studentID;
        this.gender = gender;
        this.exp = exp;
        this.photoName = image_url;
        this.introduction = introduction;
        this.open = open;
        this.type = type;
    }
}
