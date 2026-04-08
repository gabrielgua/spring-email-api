package com.gabrielgua.springemail.api.controller;

import com.gabrielgua.springemail.domain.entity.Project;
import com.gabrielgua.springemail.domain.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    public List<Project> findByUserId(@RequestParam String userId) {
        return projectService.findByUserId(userId);
    }

    @GetMapping("/{projectId}")
    public Project findById(@PathVariable String projectId) {
        return projectService.findById(projectId);
    }


}
