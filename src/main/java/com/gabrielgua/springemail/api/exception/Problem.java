package com.gabrielgua.springemail.api.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
public class Problem {

    private int status;
    private String error;
    private String message;
    private OffsetDateTime timestamp;
}
