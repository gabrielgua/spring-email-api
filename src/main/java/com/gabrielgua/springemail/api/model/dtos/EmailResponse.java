package com.gabrielgua.springemail.api.model.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Builder
public class EmailResponse {
    private String name;
    private String fromEmail;
    private String destinationEmail;
    private String message;
    private Instant timestamp;
}
