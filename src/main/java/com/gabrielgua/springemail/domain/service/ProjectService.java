package com.gabrielgua.springemail.domain.service;

import com.gabrielgua.springemail.api.utils.ProjectApiKeyGenerator;
import com.gabrielgua.springemail.api.utils.ProjectIdGenerator;
import com.gabrielgua.springemail.domain.entity.Project;
import com.gabrielgua.springemail.domain.exception.ProjectNotFoundException;
import com.gabrielgua.springemail.domain.repository.ProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@AllArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public Project findById(String id) {
        return projectRepository.findById(id).orElseThrow(ProjectNotFoundException::new);
    }

    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    public Project findByApiKey(String apiKey) {
        return projectRepository.findByApiKey(apiKey).orElseThrow(ProjectNotFoundException::new);
    }

    public List<Project> findByUserId(String userId) {
        return projectRepository.findByUserId(userId);
    }

    public Project save(Project project, String userId) {
        if (project.isNew()) {
            project.setId(ProjectIdGenerator.generate(project.getName()));
            project.setApiKey(generateApiKey());
            project.setCreatedAt(Instant.now());
            project.setActive(true);
            project.setUserId(userId);
        }

        return projectRepository.save(project);
    }

    public String regenerateApiKey(Project project) {
        var apiKey = generateApiKey();
        project.setApiKey(apiKey);
        return projectRepository.save(project).getApiKey();
    }

    private String generateApiKey() {

        var apiKey = ProjectApiKeyGenerator.generate();
        while(projectRepository.existsByApiKey(apiKey)) {
            apiKey = ProjectApiKeyGenerator.generate();
        }

        return apiKey;
    }
}
