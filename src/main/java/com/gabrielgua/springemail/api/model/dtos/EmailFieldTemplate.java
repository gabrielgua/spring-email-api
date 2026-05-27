package com.gabrielgua.springemail.api.model.dtos;

import com.gabrielgua.springemail.domain.entity.ProjectFieldType;

public record EmailFieldTemplate (
        String key,
        String label,
        String value,
        ProjectFieldType type
) {}
