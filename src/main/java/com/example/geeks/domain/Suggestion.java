package com.example.geeks.domain;

import com.example.geeks.Enum.SuggestionState;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Suggestion extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "suggestion_id")
    private Long id;

    private String title;

    private String content;

    private String photoName;

    private int agree_count;

    @Enumerated(EnumType.STRING)
    private SuggestionState suggestionState;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "suggestion")
    private List<Agree> agrees = new ArrayList<>();

    @OneToMany(mappedBy = "suggestion")
    private List<SuggestionPhoto> photos = new ArrayList<>();

    public void addAgree(Agree agree) {
        this.agrees.add(agree);
    }

    public void addPhoto(SuggestionPhoto photo) {
        this.photos.add(photo);
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public void setMember(Member member) {
        this.member = member;

        if(!member.getSuggestions().contains(this)) {
            member.addSuggestion(this);
        }
    }

    @Builder
    public Suggestion(String title, String content, String photoName, int agree_count, SuggestionState suggestionState) {
        this.title = title;
        this.content = content;
        this.photoName = photoName;
        this.agree_count = agree_count;
        this.suggestionState = suggestionState;
    }
}
