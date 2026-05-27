package com.gabrielgua.springemail.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectAllowedOrigin {
    private String id;
    private String origin;
}
