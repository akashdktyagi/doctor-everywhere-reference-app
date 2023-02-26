package com.automationfraternity.advice;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@Getter
public class CustomErrorDetails {
    private Date timestamp;
    private String message;
    private String details;
}
