package com.gabrielgua.springemail.api.model.mapper;

import com.gabrielgua.springemail.api.model.EmailRequest;
import com.gabrielgua.springemail.api.model.dtos.EmailResponse;
import com.gabrielgua.springemail.domain.entity.Project;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class EmailMapper {

    public EmailResponse toResponse(EmailRequest request, Project project) {
        return EmailResponse.builder()
                .name(request.getName())
                .fromEmail(request.getEmail())
                .destinationEmail(project.getDestinationEmail())
                .message(request.getMessage())
                .timestamp(Instant.now())
                .build();
    }
}
