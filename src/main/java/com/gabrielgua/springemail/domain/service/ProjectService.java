package com.gabrielgua.springemail.domain.service;

import com.gabrielgua.springemail.api.utils.ProjectIdGenerator;
import com.gabrielgua.springemail.domain.entity.Project;
import com.gabrielgua.springemail.domain.repository.ProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public Project findById(String id) {
        return projectRepository.findById(id).orElseThrow(() -> new RuntimeException("Project not found"));
    }

    public Project findByApiKey(String apiKey) {
        return projectRepository.findByApiKey(apiKey).orElseThrow(() -> new RuntimeException("Project not found"));
    }

    public List<Project> findByUserId(String userId) {
        return projectRepository.findByUserId(userId);
    }

    public void save(Project project) {
        project.setId(ProjectIdGenerator.generate(project.getName()));
        projectRepository.save(project);
    }
}
