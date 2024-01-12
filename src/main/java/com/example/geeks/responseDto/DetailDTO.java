package com.example.geeks.responseDto;

import com.example.geeks.Enum.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class DetailDTO {
    private boolean smoking;

    private boolean habit;

    private Ear ear;

    private Time sleep;

    private Time wakeup;

    private Out out;

    private Cleaning cleaning;

    private Tendency tendency;
}
