package com.example.geeks.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@ToString(exclude = {"suggestion"})
public class Agree {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "agree_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "suggestion_id")
    private Suggestion suggestion;

    public Agree() {
    }

    public void setMember(Member member) {
        this.member = member;

        if(!member.getAgrees().contains(this)) {
            member.addAgree(this);
        }
    }

    public void setSuggestion(Suggestion suggestion) {
        this.suggestion = suggestion;

//        if(!post.getHearts().contains(this)) {
//            post.addHeart(this);
//        }
    }
}
