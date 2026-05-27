package com.gabrielgua.springemail.api.model.mapper;

import com.gabrielgua.springemail.api.model.dtos.ProjectFieldResponse;
import com.gabrielgua.springemail.domain.entity.ProjectField;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjectFieldMapper {

    public ProjectFieldResponse toResponse(ProjectField projectField) {
        return ProjectFieldResponse.builder()
                .key(projectField.getKey())
                .label(projectField.getLabel())
                .type(projectField.getType())
                .required(projectField.isRequired())
                .build();
    }

    public List<ProjectFieldResponse> toCollectionResponse(List<ProjectField> projectFields) {
        return projectFields.stream().map(this::toResponse).toList();
    }
}
