package com.example.geeks.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Photo extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "photo_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private String photoName;

    public void setPost(Post post) {
        this.post = post;

        // 게시글에 현재 사진이 저장되지 않았으면
        if(!post.getPhotos().contains(this)){
            post.addPhoto(this);
        }
    }

    public Photo(String photoName) {
        this.photoName = photoName;
    }

    public static Photo createPhoto(String fileName, Post post) {
        Photo photo = new Photo(fileName);
        photo.setPost(post);
        return photo;
    }
}
