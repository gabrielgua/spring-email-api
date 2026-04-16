package com.gabrielgua.springemail.api.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.Map;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Problem {

    private int status;
    private String error;
    private String message;
    private OffsetDateTime timestamp;
    private Map<String, Object> fields;
}
