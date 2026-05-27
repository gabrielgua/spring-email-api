package com.gabrielgua.springemail.api.model.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProjectAllowedOriginRequest {

    @NotEmpty
    public List<String> origins;
}
