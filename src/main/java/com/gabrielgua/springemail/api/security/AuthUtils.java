package com.gabrielgua.springemail.api.security;

import com.gabrielgua.springemail.domain.entity.UserRole;
import com.gabrielgua.springemail.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class AuthUtils {

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public String getAuthenticatedUserId() {
        var auth = getAuthentication();

        if (!isAuthenticated(auth)) {
            throw new AccessDeniedException("User is not authenticated");
        }

        return getAuthenticatedUser().getId();
    }

    public CustomUserPrincipal getAuthenticatedUser() {
        return (CustomUserPrincipal) getAuthentication().getPrincipal();
    }

    public boolean isAuthenticated() {
        return isAuthenticated(getAuthentication());
    }

    private boolean isAuthenticated(Authentication auth) {
        return auth != null
                && auth.isAuthenticated()
                && !(auth.getPrincipal() instanceof String
                && auth.getPrincipal().equals("anonymousUser"));
    }


    public boolean isAdmin() {
        var auth = getAuthentication();

        if (!isAuthenticated(auth)) {
            return false;
        }

        return auth.getAuthorities().stream()
                .anyMatch(a -> Objects.equals(a.getAuthority(), UserRole.ROLE_ADMIN.name()));
    }
}
