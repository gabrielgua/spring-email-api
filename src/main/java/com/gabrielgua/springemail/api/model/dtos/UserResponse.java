package com.gabrielgua.springemail.api.model.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gabrielgua.springemail.domain.entity.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {

    private String id;
    private String name;
    private UserRole role;
    private String email;
    private Instant createdAt;
    private List<String> projectIds;
}
