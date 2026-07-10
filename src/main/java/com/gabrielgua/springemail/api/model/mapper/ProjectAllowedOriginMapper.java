package com.gabrielgua.springemail.api.model.mapper;

import com.gabrielgua.springemail.api.model.dtos.ProjectAllowedOriginResponse;
import com.gabrielgua.springemail.domain.entity.ProjectAllowedOrigin;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjectAllowedOriginMapper {

    public ProjectAllowedOriginResponse toResponse(ProjectAllowedOrigin projectAllowedOrigin) {
        return ProjectAllowedOriginResponse.builder()
                .id(projectAllowedOrigin.getId())
                .origin(projectAllowedOrigin.getOrigin())
                .build();
    }

    public List<ProjectAllowedOriginResponse> toCollectionResponse(List<ProjectAllowedOrigin> projectAllowedOrigins) {
        return projectAllowedOrigins.stream().map(this::toResponse).toList();
    }
}
