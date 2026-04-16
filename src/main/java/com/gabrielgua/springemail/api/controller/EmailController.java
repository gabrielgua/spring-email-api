package com.gabrielgua.springemail.api.controller;

import com.gabrielgua.springemail.api.model.EmailRequest;
import com.gabrielgua.springemail.api.model.dtos.EmailResponse;
import com.gabrielgua.springemail.api.model.mapper.EmailMapper;
import com.gabrielgua.springemail.domain.entity.Project;
import com.gabrielgua.springemail.domain.service.EmailService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/emails")
public class EmailController {

    private final EmailService emailService;
    private final EmailMapper emailMapper;

    @PostMapping
    public ResponseEntity<EmailResponse> sendEmail(@RequestBody EmailRequest emailRequest, HttpServletRequest request) {
        var project = (Project) request.getAttribute("project");
        emailService.sendEmail(project, emailRequest);
        return ResponseEntity.ok(emailMapper.toResponse(emailRequest, project));
    }
}
