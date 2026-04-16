package com.gabrielgua.springemail.domain.service;

import com.gabrielgua.springemail.api.model.EmailRequest;
import com.gabrielgua.springemail.domain.entity.Project;
import com.gabrielgua.springemail.domain.exception.BusinessException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.time.Year;

@Service
@AllArgsConstructor
public class EmailService {

    private final ProjectService projectService;
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    public void sendEmail(Project project, EmailRequest request) {

        Context context = new Context();
        context.setVariable("name", request.getName());
        context.setVariable("email", request.getEmail());
        context.setVariable("message", request.getMessage());
        context.setVariable("projectName", project.getName());
        context.setVariable("year", Year.now().getValue());

        String html = templateEngine.process("email/contact", context);

        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            var helper = new MimeMessageHelper(message, "UTF-8");
            helper.setFrom("no-reply@emailautomatico.com");
            helper.setTo(project.getDestinationEmail());
            helper.setReplyTo(project.getDestinationEmail());
            helper.setSubject("Novo Contato - " + project.getName());
            helper.setText(html, true);

            javaMailSender.send(message);
        } catch (Exception e) {
            throw new BusinessException("Failed to send email: " + e.getMessage());
        }
    }
}
