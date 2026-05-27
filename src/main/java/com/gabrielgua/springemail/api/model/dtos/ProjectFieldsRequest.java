package com.gabrielgua.springemail.api.model.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Getter
@Setter
public class ProjectFieldsRequest {

    @Valid
    @NotEmpty
    private List<ProjectFieldRequest> fields;
}
