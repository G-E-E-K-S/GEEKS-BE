package com.example.geeks.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@ToString
public class PostScrap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_scrap_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public PostScrap() {

    }

    public PostScrap(Member member, Post post) {
        this.member = member;
        this.post = post;
    }

    public void setMember(Member member) {
        this.member = member;

        if(!member.getScraps().contains(this)) {
            member.addScrap(this);
        }
    }

    public void setPost(Post post) {
        this.post = post;

        if(!post.getScraps().contains(this)) {
            post.addScrap(this);
        }
    }
}