package com.example.geeks.requestDto;

import com.example.geeks.Enum.DormitoryType;
import com.example.geeks.Enum.Gender;
import lombok.Getter;

@Getter
public class RegisterDTO {
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
