package com.example.geeks.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    private String content;

    @ColumnDefault("FALSE")
    private boolean isDeleted;

    private boolean anonymity;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_comment_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent")
    private List<Comment> children = new ArrayList<>();

    public Comment(String content, boolean isDeleted, boolean anonymity) {
        this.content = content;
        this.isDeleted = isDeleted;
        this.anonymity = anonymity;
    }

    public void setMember(Member member) {
        this.member = member;

        if(!member.getComments().contains(this)) {
            member.addComment(this);
        }
    }

    public void setPost(Post post) {
        this.post = post;

        if(!post.getComments().contains(this)) {
            post.addComment(this);
        }
    }

    public void setParent(Comment comment) {
        this.parent = comment;

        if(!comment.children.contains(this)) {
            comment.children.add(this);
        }
    }

    public void setDeleted(boolean deleted) {
        this.isDeleted = deleted;
    }

    public void addComment(Comment comment) {
        this.children.add(comment);
    }
}
