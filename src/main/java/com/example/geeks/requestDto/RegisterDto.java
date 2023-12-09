package com.example.geeks.requestDto;

import com.example.geeks.Enum.DormitoryType;
import com.example.geeks.Enum.Gender;
import lombok.Getter;

import java.lang.reflect.Type;

@Getter
public class RegisterDto {
    private Long id;

    private String nickname;

    private String email;

    private String password;

    private String major;

    private Gender gender;

    private DormitoryType type;

    private int exp;

    private boolean smocking;
}
