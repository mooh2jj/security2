package com.example.security2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class ResultDto {

    private boolean result;
    private String msg;
    private Object object;

}
