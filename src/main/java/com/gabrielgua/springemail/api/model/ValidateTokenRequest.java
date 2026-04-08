package com.gabrielgua.springemail.api.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidateTokenRequest {

    @NotBlank
    private String userId;

    @NotBlank
    private String token;
}
