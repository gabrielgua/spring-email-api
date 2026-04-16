package com.gabrielgua.springemail.api.model.mapper;

import com.gabrielgua.springemail.api.model.dtos.ProjectResponse;
import com.gabrielgua.springemail.domain.entity.Project;
import com.gabrielgua.springemail.domain.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProjectMapper {

    private final ProjectService projectService;

    public ProjectResponse toResponse(Project project) {
        return ProjectResponse.builder()
                .id(project.getId())
                .name(project.getName())
                .destinationEmail(project.getDestinationEmail())
                .active(project.getActive())
                .createdAt(project.getCreatedAt())
                .build();
    }

    public ProjectResponse toResponseEdit(Project project) {
        return ProjectResponse.builder()
                .id(project.getId())
                .apiKey(project.getApiKey())
                .destinationEmail(project.getDestinationEmail())
                .allowedOrigins(project.getAllowedOrigins())
                .build();
    }

    public List<ProjectResponse> toResponseList(List<Project> projects) {
        return projects.stream()
                .map(this::toResponse)
                .toList();
    }
}
