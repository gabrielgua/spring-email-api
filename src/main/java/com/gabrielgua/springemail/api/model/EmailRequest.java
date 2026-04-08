package com.gabrielgua.springemail.api.model;

import lombok.Data;

@Data
public class EmailRequest {
    private String name;
    private String email;
    private String message;
}
