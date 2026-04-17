package com.gabrielgua.springemail.api.security;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public @interface CheckSecurity {

    public @interface General {
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("hasRole('ADMIN')")
        public @interface isAdmin { }

    }

    public @interface Users {
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("@authorizationService.canManageUser(#userId)")
        public @interface canManage { }

    }

    public @interface Projects {
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("@authorizationService.canManageProject(#projectId)")
        public @interface canManage { }

        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("@authorizationService.canListProjects(#userId)")
        public @interface canList { }
    }
}
