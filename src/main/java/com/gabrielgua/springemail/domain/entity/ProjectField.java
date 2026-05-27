package com.gabrielgua.springemail.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectField {
    private String key;
    private String label;
    private ProjectFieldType type;
    private boolean required;
}
