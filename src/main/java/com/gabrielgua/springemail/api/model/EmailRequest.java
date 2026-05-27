package com.gabrielgua.springemail.api.model;

import lombok.Data;

import java.util.Map;

@Data
public class EmailRequest {
    private Map<String, String> fields;
}
