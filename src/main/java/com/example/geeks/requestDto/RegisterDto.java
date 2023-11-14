package com.example.geeks.requestDto;

import lombok.Getter;

@Getter
public class RegisterDto {
    private Long id;

    private String nickname;

    private String email;

    private String password;

    private String major;

    private int gender;

    private int exp;

    private boolean smocking;
}
