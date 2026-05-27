package com.gabrielgua.springemail.api.model.mapper;

import com.gabrielgua.springemail.api.model.dtos.ProjectRequest;
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
    private final ProjectAllowedOriginMapper projectAllowedOriginMapper;
    private final ProjectFieldMapper projectFieldMapper;

    public ProjectResponse toResponse(Project project) {
        return ProjectResponse.builder()
                .id(project.getId())
                .name(project.getName())
                .destinationEmail(project.getDestinationEmail())
                .active(project.getActive())
                .createdAt(project.getCreatedAt())
                .build();
    }

    public ProjectResponse toFullResponse(Project project) {
        return ProjectResponse.builder()
                .id(project.getId())
                .name(project.getName())
                .active(project.getActive())
                .apiKey(project.getApiKey())
                .destinationEmail(project.getDestinationEmail())
                .allowedOrigins(projectAllowedOriginMapper.toCollectionResponse(project.getAllowedOrigins()))
                .fields(projectFieldMapper.toCollectionResponse(project.getFields()))
                .createdAt(project.getCreatedAt())
                .build();
    }

    public List<ProjectResponse> toResponseList(List<Project> projects) {
        return projects.stream()
                .map(this::toResponse)
                .toList();
    }

    public Project toEntity(ProjectRequest projectRequest) {
        var project = new Project();
        project.setName(projectRequest.getName());
        project.setDestinationEmail(projectRequest.getDestinationEmail());
        return project;
    }
}
