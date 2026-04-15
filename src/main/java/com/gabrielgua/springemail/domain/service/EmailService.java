package com.gabrielgua.springemail.domain.service;

import com.gabrielgua.springemail.api.model.EmailRequest;
import com.gabrielgua.springemail.domain.entity.Project;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailService {

    private final ProjectService projectService;
    private final JavaMailSender javaMailSender;

    public void sendEmail(Project project, EmailRequest request) {

        var message = new SimpleMailMessage();

        message.setTo(project.getDestinationEmail());
        message.setFrom("no-reply@springemail.com");
        message.setReplyTo(request.getEmail());
        message.setSubject("Novo Contato - " + project.getName());
        message.setText(buildBody(project, request));

        javaMailSender.send(message);
    }

    private String buildBody(Project project, EmailRequest req) {
        return """
                Novo contato do site - %s,

                Nome: %s
                Email: %s

                Mensagem:
                %s
                """.formatted(
                        project.getName(),
                req.getName(),
                req.getEmail(),
                req.getMessage()
        );
    }
}
