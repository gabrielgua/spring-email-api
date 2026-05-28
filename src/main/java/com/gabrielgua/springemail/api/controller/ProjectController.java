package com.gabrielgua.springemail.api.controller;

import com.gabrielgua.springemail.api.model.dtos.ProjectRequest;
import com.gabrielgua.springemail.api.model.dtos.ProjectResponse;
import com.gabrielgua.springemail.api.model.mapper.ProjectMapper;
import com.gabrielgua.springemail.api.security.AuthUtils;
import com.gabrielgua.springemail.api.security.CheckSecurity;
import com.gabrielgua.springemail.domain.entity.Project;
import com.gabrielgua.springemail.domain.service.ProjectService;
import com.gabrielgua.springemail.domain.service.UserService;
import com.mongodb.lang.NonNullApi;
import com.mongodb.lang.Nullable;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/projects")
public class ProjectController {

    private final UserService userService;
    private final ProjectMapper projectMapper;
    private final ProjectService projectService;
    private final AuthUtils authUtils;

    @GetMapping
    @CheckSecurity.Projects.canList
    public List<ProjectResponse> findByUserId(@Nullable @RequestParam String userId) {
        if (userId == null) {
            return projectMapper.toResponseList(projectService.findAll());
        }
        var user = userService.findById(userId);
        return projectMapper.toResponseList(projectService.findByUserId(user.getId()));
    }

    @GetMapping("/{projectId}")
    @CheckSecurity.Projects.canManage
    public ProjectResponse findById(@PathVariable String projectId) {
        return projectMapper.toFullResponse(projectService.findById(projectId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectResponse saveNew(@RequestBody ProjectRequest request) {
        var project = projectMapper.toEntity(request);
        return projectMapper.toResponse(projectService.save(project, resolveProjectOwner(request)));
    }

    @PostMapping("/{projectId}/regenerate-api-key")
    @CheckSecurity.Projects.canManage
    public String regenerateApiKey(@PathVariable String projectId) {
        var project = projectService.findById(projectId);
        return projectService.regenerateApiKey(project);
    }

    private String resolveProjectOwner(ProjectRequest request) {
        if (authUtils.isAdmin() && request.getUserId() != null) {
            return request.getUserId();
        }

        return authUtils.getAuthenticatedUserId();
    }

}
