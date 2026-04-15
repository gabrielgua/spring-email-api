package com.gabrielgua.springemail.infra.filter;

import com.gabrielgua.springemail.domain.entity.Project;
import com.gabrielgua.springemail.domain.repository.ProjectRepository;
import com.gabrielgua.springemail.domain.service.RateLimiterService;
import com.gabrielgua.springemail.exception.OutputResponseHelper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@AllArgsConstructor
public class ApiKeyFilter extends OncePerRequestFilter {

    private final RateLimiterService rateLimiterService;
    private final ProjectRepository projectRepository;
    private final OutputResponseHelper outputResponseHelper;
    private static final String EMAIL_ENDPOINT = "/api/emails";

    private static final Logger log = LoggerFactory.getLogger(ApiKeyFilter.class);

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

        //valida se foi passado apikey
        if (apiKey == null || apiKey.isEmpty()) {
            error(request, response, "MISSING_API_KEY", HttpStatus.UNAUTHORIZED, "Missing API Key", apiKey);
            return;
        }

        //valida se apikey é valida e existe projeto com ela
        Optional<Project> project = projectRepository.findByApiKey(apiKey);

        if (project.isEmpty() || !project.get().getActive()) {
            error(request, response, "INVALID_API_KEY", HttpStatus.UNAUTHORIZED, "Invalid API Key", apiKey);
            return;
        }

        //rate limiter por api key
        if (!rateLimiterService.tryConsume(apiKey)) {
            error(request, response, "TOO_MANY_REQUESTS", HttpStatus.TOO_MANY_REQUESTS, "Too Many Requests", apiKey);
            return;
        }

        //valida se a origin da request é allowed pelo projeto
        if (!project.get().getAllowedOrigins().isEmpty()) {
            if (!project.get().getAllowedOrigins().contains(origin)) {
                error(request, response, "ORIGIN_NOT_ALLOWED",  HttpStatus.FORBIDDEN, "Origin not allowed", apiKey);
                return;
            }
        }



        //seta o project na request pro controller ter o acesso
        request.setAttribute("project", project.get());

        filterChain.doFilter(request, response);
    }

    private void error(HttpServletRequest request, HttpServletResponse response, String error, HttpStatus status, String message, String apiKey) throws ServletException, IOException {
        outputResponseHelper.error(response, status, error, message);
        logBlocked(request, apiKey, error);
    }

    private void logBlocked(HttpServletRequest request, String apiKey, String reason) {
        String ip = getClientIP(request);
        String userAgent = request.getHeader("User-Agent");
        String origin = request.getHeader("Origin");

        log.warn("BLOCKED REQUEST ip={} apiKey={} origin={} userAgent={} reason={}", ip, apiKey, origin, userAgent, reason);
    }

    private String getClientIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip != null && !ip.isBlank()) {
            return ip.split(",")[0];
        }
        return request.getRemoteAddr();
    }
}
