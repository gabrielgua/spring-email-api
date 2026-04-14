package com.gabrielgua.springemail.api.filter;

import com.gabrielgua.springemail.domain.entity.Project;
import com.gabrielgua.springemail.domain.repository.ProjectRepository;
import com.gabrielgua.springemail.exception.OutputResponseHelper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@AllArgsConstructor
public class ApiKeyFilter extends OncePerRequestFilter {

    private final ProjectRepository projectRepository;
    private final OutputResponseHelper outputResponseHelper;
    private static final String EMAIL_ENDPOINT = "/api/emails";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //só passa pela url de sendar email e se for POST
        if (!request.getServletPath().equals(EMAIL_ENDPOINT)
                || !"POST".equalsIgnoreCase(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }

        String apiKey = request.getHeader("x-api-key");
        String origin = request.getHeader("Origin");
        System.out.println("x-api-key: " + apiKey);
        System.out.println("Origin: " + origin);

        //valida se foi passado apikey
        if (apiKey == null || apiKey.isEmpty()) {
            outputResponseHelper.error(response, HttpStatus.UNAUTHORIZED, "MISSING_API_KEY", "Missing API key");
            return;
        }

        Optional<Project> project = projectRepository.findByApiKey(apiKey);

        //valida se apikey é valida e existe projeto com ela
        if (project.isEmpty() || !project.get().getActive()) {
            outputResponseHelper.error(response, HttpStatus.UNAUTHORIZED, "INVALID_API_KEY", "Invalid API Key");
            return;
        }

        //valida se a origin da request é allowed pelo projeto
        if (!project.get().getAllowedOrigins().isEmpty()) {
            if (!project.get().getAllowedOrigins().contains(origin)) {
                outputResponseHelper.error(response, HttpStatus.FORBIDDEN, "ORIGIN_NOT_ALLOWED", "Invalid Origin");
                return;
            }
        }

        //seta o project id na request pro controller ter o acesso ao projeto correto
        request.setAttribute("projectId", project.get().getId());

        filterChain.doFilter(request, response);
    }
}
