package com.gabrielgua.springemail.api.model.dtos;

import com.gabrielgua.springemail.domain.entity.ProjectFieldType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectFieldRequest {

    @NotBlank
    private String key;

    @NotBlank
    private String label;

    @NotNull
    private ProjectFieldType type;

    private boolean required;

    private boolean replyTo;
}
