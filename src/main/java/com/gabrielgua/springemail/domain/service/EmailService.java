package com.gabrielgua.springemail.domain.service;

import com.gabrielgua.springemail.api.model.EmailRequest;
import com.gabrielgua.springemail.api.model.dtos.EmailFieldTemplate;
import com.gabrielgua.springemail.domain.entity.Project;
import com.gabrielgua.springemail.domain.entity.ProjectField;
import com.gabrielgua.springemail.domain.entity.ProjectFieldType;
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

        var templateFields = project.getFields().stream()
                .map(field -> new EmailFieldTemplate(
                        field.getKey(),
                        field.getLabel(),
                        request.getFields().get(field.getKey()),
                        field.getType()
                ))
                .toList();

        Context context = new Context();

        String replyTo = project.getFields().stream()
                .filter(ProjectField::isReplyTo)
                .map(field -> request.getFields().get(field.getKey()))
                .findFirst()
                .orElse(null);

        context.setVariable("fields", templateFields);
        context.setVariable("projectName", project.getName());
        context.setVariable("replyTo", replyTo);
        context.setVariable("year", Year.now().getValue());

        String html = templateEngine.process("email/contact", context);

        try {

            Resend resend = new Resend(resendApiKey);

            CreateEmailOptions params = CreateEmailOptions.builder()
                    .from(resendEmailName)
                    .to(project.getDestinationEmail())
                    .replyTo(replyTo)
                    .subject("Nova Mensagem - " + project.getName())
                    .html(html)
                    .build();

            resend.emails().send(params);

        } catch (Exception e) {
            throw new BusinessException("Failed to send email: " + e.getMessage());
        }
    }
}