package com.gabrielgua.springemail.domain.service;

import com.gabrielgua.springemail.api.utils.ProjectIdGenerator;
import com.gabrielgua.springemail.domain.entity.Project;
import com.gabrielgua.springemail.domain.exception.ProjectNotFoundException;
import com.gabrielgua.springemail.domain.repository.ProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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

    public void save(Project project) {
        project.setId(ProjectIdGenerator.generate(project.getName()));
        projectRepository.save(project);
    }
}
