package com.example.geeks.requestDto;

import com.example.geeks.Enum.*;
import lombok.Getter;

@Getter
public class DetailDto {
    private boolean smoking;

    private boolean habit;

    private Ear ear;

    private Time sleep;

    private Time wakeup;

    private Out out;

    private Cleaning cleaning;

    private Tendency tendency;
}
