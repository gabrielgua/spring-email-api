package com.gabrielgua.springemail.domain.service;

import com.gabrielgua.springemail.api.model.EmailRequest;
import com.gabrielgua.springemail.domain.entity.Project;
import com.gabrielgua.springemail.domain.exception.BusinessException;
import com.resend.Resend;
import com.resend.services.emails.model.CreateEmailOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.time.Year;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final SpringTemplateEngine templateEngine;

    @Value("${RESEND_API_KEY}")
    private String resendApiKey;

    @Value("${RESEND_EMAIL_NAME}")
    private String resendEmailName;

    public void sendEmail(Project project, EmailRequest request) {

        Context context = new Context();
        context.setVariable("name", request.getName());
        context.setVariable("email", request.getEmail());
        context.setVariable("message", request.getMessage());
        context.setVariable("subject", request.getSubject());
        context.setVariable("projectName", project.getName());
        context.setVariable("year", Year.now().getValue());

        String html = templateEngine.process("email/contact", context);

        try {

            Resend resend = new Resend(resendApiKey);

            CreateEmailOptions params = CreateEmailOptions.builder()
                    .from(resendEmailName)
                    .to(project.getDestinationEmail())
                    .replyTo(request.getEmail())
                    .subject(request.getSubject() + " - " + project.getName())
                    .html(html)
                    .build();

            resend.emails().send(params);

        } catch (Exception e) {
            throw new BusinessException("Failed to send email: " + e.getMessage());
        }
    }
}