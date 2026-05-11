package com.gabrielgua.springemail.api.model.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Getter
@Setter
public class ProjectRequest {

    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String destinationEmail;

    @NotEmpty
    private List<String> allowedOrigins;
}
