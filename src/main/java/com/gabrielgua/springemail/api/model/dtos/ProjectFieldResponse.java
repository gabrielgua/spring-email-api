package com.gabrielgua.springemail.api.model.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gabrielgua.springemail.domain.entity.ProjectFieldType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProjectFieldResponse {

    private String key;
    private String label;
    private ProjectFieldType type;
    private boolean required;
}
