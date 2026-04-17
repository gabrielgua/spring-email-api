package com.gabrielgua.springemail.api.security;

import com.gabrielgua.springemail.domain.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthorizationService {

    private final ProjectService projectService;
    private final AuthUtils authUtils;

    public boolean canManageUser(String userId) {
        if (authUtils.isAdmin()) return true;
        return authUtils.getAuthenticatedUser().getId().equals(userId);
    }

    public boolean canManageProject(String projectId) {
        if (authUtils.isAdmin()) {
            return true;
        }

        var project = projectService.findById(projectId);
        return project.getUserId().equals(authUtils.getAuthenticatedUser().getId());
    }

    public boolean canListProjects(String userId) {
        if (authUtils.isAdmin()) {
            return true;
        }

        return authUtils.getAuthenticatedUser().getId().equals(userId);
    }
}

