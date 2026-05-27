package com.gabrielgua.springemail.api.controller;

import com.gabrielgua.springemail.api.model.dtos.ProjectFieldsRequest;
import com.gabrielgua.springemail.api.model.mapper.ProjectFieldMapper;
import com.gabrielgua.springemail.api.security.CheckSecurity;
import com.gabrielgua.springemail.domain.entity.ProjectField;
import com.gabrielgua.springemail.domain.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects/{projectId}/fields")
@RequiredArgsConstructor
public class ProjectFieldController {

    private final ProjectService projectService;
    private final ProjectFieldMapper  projectFieldMapper;

    @PostMapping
    @CheckSecurity.Projects.canManage
    public ResponseEntity<ProjectField> addProjectFields(@PathVariable String projectId, @Valid @RequestBody ProjectFieldsRequest request) {
        var project = projectService.findById(projectId);
        projectService.addProjectFields(project, projectFieldMapper.toCollectionEntity(request.getFields()));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{fieldKey}")
    @CheckSecurity.Projects.canManage
    public ResponseEntity<ProjectField> deleteProjectField(@PathVariable String projectId, @Valid @PathVariable String fieldKey) {
        var project = projectService.findById(projectId);
        projectService.removeField(project, fieldKey);
        return  ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
