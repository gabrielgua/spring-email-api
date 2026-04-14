package com.gabrielgua.springemail.api.filter;

import com.gabrielgua.springemail.domain.repository.ProjectRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class ApiKeyFilter extends OncePerRequestFilter {

    private final ProjectRepository projectRepository;

    private static final String[] POST_ENDPOINTS = {"/api/emails/send"};

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {




    }
}
