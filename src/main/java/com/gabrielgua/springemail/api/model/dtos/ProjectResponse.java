package com.gabrielgua.springemail.api.model.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProjectResponse {

    private String id;
    private String name;
    private String apiKey;
    private String destinationEmail;
    private Boolean active;
    private Instant createdAt;
    private List<String> allowedOrigins;
    private String userId;

}
