package com.gabrielgua.springemail.api.controller;

import com.gabrielgua.springemail.api.model.dtos.ProjectAllowedOriginRequest;
import com.gabrielgua.springemail.api.security.CheckSecurity;
import com.gabrielgua.springemail.domain.entity.ProjectAllowedOrigin;
import com.gabrielgua.springemail.domain.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects/{projectId}/origins")
@RequiredArgsConstructor
public class ProjectAllowedOriginsController {

    private final ProjectService projectService;

    @PostMapping
    @CheckSecurity.Projects.canManage
    public ResponseEntity<?> addAllowedOrigins(@PathVariable String projectId, @Valid @RequestBody ProjectAllowedOriginRequest request) {
        var project = projectService.findById(projectId);
        projectService.addAllowedOrigins(project, request.getOrigins());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{originId}")
    @CheckSecurity.Projects.canManage
    public ResponseEntity<?> deleteAllowedOrigins(@PathVariable String projectId, @PathVariable String originId) {
        var project = projectService.findById(projectId);
        projectService.removeAllowedOrigin(project, originId);
        return ResponseEntity.noContent().build();
    }
}
