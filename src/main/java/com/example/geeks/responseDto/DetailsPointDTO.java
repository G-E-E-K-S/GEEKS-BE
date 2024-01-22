package com.example.geeks.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DetailsPointDTO {
    int point;

    List<DetailDTO> details = new ArrayList<>();
}
