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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SuggestionPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "photo_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "suggestion_id")
    private Suggestion suggestion;

    private String photoName;

    public void setSuggestion(Suggestion suggestion) {
        this.suggestion = suggestion;

        if(!suggestion.getPhotos().contains(this)) {
            suggestion.addPhoto(this);
        }
    }

    public SuggestionPhoto(String photoName) {
        this.photoName = photoName;
    }

    public static SuggestionPhoto createPhoto(String fileName, Suggestion suggestion) {
        SuggestionPhoto photo = new SuggestionPhoto(fileName);
        photo.setSuggestion(suggestion);
        return photo;
    }
}
