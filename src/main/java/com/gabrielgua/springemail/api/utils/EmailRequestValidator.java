package com.gabrielgua.springemail.api.utils;

import com.gabrielgua.springemail.api.model.EmailRequest;
import com.gabrielgua.springemail.domain.entity.Project;
import com.gabrielgua.springemail.domain.entity.ProjectField;
import com.gabrielgua.springemail.domain.exception.BusinessException;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class EmailRequestValidator {

    public void validate(Project project, EmailRequest request) {
        validateRequiredFields(project, request);

        validateUnknownFields(project, request);

        validateFieldTypes(project, request);
    }

    private void validateRequiredFields(Project project, EmailRequest request) {
        for (ProjectField field : project.getFields()) {

            if (!field.isRequired()) {
                continue;
            }

            String value = request.getFields().get(field.getKey());

            if (value == null || value.isBlank()) {
                throw new BusinessException(
                        "Field '%s' is required".formatted(field.getKey())
                );
            }
        }
    }

    private void validateUnknownFields(Project project, EmailRequest request) {

        var allowedKeys = project.getFields().stream()
                .map(ProjectField::getKey)
                .collect(Collectors.toSet());

        for (String key : request.getFields().keySet()) {

            if (!allowedKeys.contains(key)) {
                throw new BusinessException(
                        "Field '%s' does not exist".formatted(key)
                );
            }
        }
    }

    private void validateFieldTypes(Project project, EmailRequest request) {

        for (ProjectField field : project.getFields()) {

            String value = request.getFields().get(field.getKey());

            if (value == null || value.isBlank()) {
                continue;
            }

            switch (field.getType()) {
                case EMAIL -> validateEmail(field.getKey(), value);
                case NUMBER -> validateNumber(field.getKey(), value);
            }
        }
    }

    private void validateEmail(String key, String value) {

        boolean valid = value.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

        if (!valid) {
            throw new BusinessException(
                    "Field '%s' must be a valid email".formatted(key)
            );
        }
    }

    private void validateNumber(String key, String value) {
        try {
            Double.parseDouble(value);
        } catch (Exception ex) {
            throw new BusinessException(
                    "Field '%s' must be a number".formatted(key)
            );
        }
    }
}
