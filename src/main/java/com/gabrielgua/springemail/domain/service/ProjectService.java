package com.gabrielgua.springemail.domain.service;

import com.gabrielgua.springemail.api.utils.ProjectApiKeyGenerator;
import com.gabrielgua.springemail.api.utils.ProjectIdGenerator;
import com.gabrielgua.springemail.domain.entity.Project;
import com.gabrielgua.springemail.domain.entity.ProjectAllowedOrigin;
import com.gabrielgua.springemail.domain.entity.ProjectField;
import com.gabrielgua.springemail.domain.exception.*;
import com.gabrielgua.springemail.domain.repository.ProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProjectService {

    private static final int PROJECT_FIELDS_MAX_VALUE = 20;
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
        if (project.getFields() == null) {
            project.setFields(List.of());
        }

        normalizeProjectFieldKeys(project.getFields());
        validateFields(project.getFields());

        if (project.isNew()) {
            prepareNewProject(project, userId);
        }

        return projectRepository.save(project);
    }

    public void addAllowedOrigins(Project project, List<String> origins) {

        if (project.getAllowedOrigins() == null) {
            project.setAllowedOrigins(new ArrayList<>());
        }

        for (String origin : origins) {

            boolean duplicated = project.getAllowedOrigins().stream()
                    .anyMatch(o -> o.getOrigin().equals(origin.trim().toLowerCase()));

            if (duplicated) {
                throw new DuplicatedAllowedOriginException();
            }

            project.getAllowedOrigins().add(new ProjectAllowedOrigin(
                    UUID.randomUUID().toString(), origin.trim().toLowerCase()
            ));
        }

        projectRepository.save(project);
    }

    public void removeAllowedOrigin(Project project, String originId) {
        var removed = project.getAllowedOrigins().removeIf(origin -> origin.getId().equals(originId));

        if (!removed) {
            throw new AllowedOriginNotFoundException();
        }

        projectRepository.save(project);
    }

    public void addProjectFields(Project project, List<ProjectField> fields) {
        for (ProjectField field : fields) {

            field.setKey(field.getKey().trim().toLowerCase());

            boolean duplicated = project.getFields().stream()
                    .anyMatch(f -> f.getKey().equals(field.getKey()));

            if (duplicated) {
                throw new DuplicatedProjectFieldException();
            }

            if (project.getFields().size() >= PROJECT_FIELDS_MAX_VALUE) {
                throw new ProjectFieldsLimitExceededException();
            }

            project.getFields().add(field);
        }

        save(project, project.getUserId());
    }

    public void removeField(Project project, String fieldKey) {
        var removed = project.getFields().removeIf(field -> field.getKey().equals(fieldKey.trim().toLowerCase()));

        if (!removed) {
            throw new ProjectFieldNotFoundException();
        }

        save(project, project.getUserId());
    }

    private void normalizeProjectFieldKeys(List<ProjectField> fields) {
        if (fields == null) {
            return;
        }

        fields.forEach(field -> field.setKey(field.getKey().trim().toLowerCase()));
    }

    private void prepareNewProject(Project project, String userId) {
        project.setId(ProjectIdGenerator.generate(project.getName()));
        project.setApiKey(generateApiKey());
        project.setCreatedAt(Instant.now());
        project.setActive(true);
        project.setUserId(userId);

        normalizeProjectFieldKeys(project.getFields());
    }

    private void validateFields(List<ProjectField> fields) {
        var invalidKeys = fields.stream()
                .anyMatch(field -> field.getKey() == null || field.getKey().trim().isEmpty());

        if (invalidKeys) {
            throw new InvalidProjectFieldException();
        }

        var duplicated = fields.stream()
                .collect(Collectors.groupingBy(ProjectField::getKey, Collectors.counting()))
                .entrySet()
                .stream()
                .anyMatch(entry -> entry.getValue() > 1);

        if (duplicated) {
            throw new DuplicatedProjectFieldException();
        }

        if (fields.size() > PROJECT_FIELDS_MAX_VALUE) {
            throw new ProjectFieldsLimitExceededException("Project fields must not exceed " +  PROJECT_FIELDS_MAX_VALUE);
        }
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
