package com.gabrielgua.springemail.api.controller;

import com.gabrielgua.springemail.api.model.dtos.ProjectResponse;
import com.gabrielgua.springemail.api.model.mapper.ProjectMapper;
import com.gabrielgua.springemail.domain.entity.Project;
import com.gabrielgua.springemail.domain.service.ProjectService;
import com.gabrielgua.springemail.domain.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/projects")
public class ProjectController {

    private final UserService userService;
    private final ProjectMapper projectMapper;
    private final ProjectService projectService;

    @GetMapping
    public List<ProjectResponse> findByUserId(@RequestParam String userId) {
        var user = userService.findById(userId);
        return projectMapper.toResponseList(projectService.findByUserId(user.getId()));
    }

    @GetMapping("/{projectId}")
    public ProjectResponse findById(@PathVariable String projectId) {
        return projectMapper.toResponse(projectService.findById(projectId));
    }
}
