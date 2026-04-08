package com.gabrielgua.springemail.api.controller;

import com.gabrielgua.springemail.api.model.EmailRequest;
import com.gabrielgua.springemail.domain.service.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/emails")
public class EmailController {

    private final EmailService emailService;
    private static final String TEST_API_KEY = "proj_test_api_key_123987789132";

    @PutMapping
    public void sendEmail(@RequestBody EmailRequest request) {
        emailService.sendEmail(TEST_API_KEY, request);
    }
}
