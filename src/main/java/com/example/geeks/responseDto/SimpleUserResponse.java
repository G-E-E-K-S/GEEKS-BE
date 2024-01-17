package com.example.geeks.responseDto;

import com.example.geeks.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SimpleUserResponse {
    private Long id;
    private String email;
    private String password;
    private String nickname;

    public SimpleUserResponse(Long id, String email, String password, String nickname) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;

    }

    public static SimpleUserResponse of(Member user) {
        return new SimpleUserResponse(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getNickname()
        );
    }
}
