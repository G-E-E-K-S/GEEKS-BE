package com.example.geeks.domain;


import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@ToString
public class Heart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "heart_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public Heart() {
    }

    public Heart(Member member, Post post) {
        this.member = member;
        this.post = post;
    }

    public void setMember(Member member) {
        this.member = member;

        if(!member.getHearts().contains(this)) {
            member.addHeart(this);
        }
    }

    public void setPost(Post post) {
        this.post = post;

        if(!post.getHearts().contains(this)) {
            post.addHeart(this);
        }
    }
}
