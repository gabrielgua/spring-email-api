package com.gabrielgua.springemail.api.utils;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.text.Normalizer;
import java.util.Locale;

@Component
public class ProjectIdGenerator {

    private static final SecureRandom RANDOM = new SecureRandom();
    private static final String ALPHANUMERIC = "abcdefghijklmnopqrstuvwxyz0123456789";

    public static String generate(String projectName) {
        String prefix = slugify(projectName);
        String random = randomString(6);

        return (prefix + "-" + random).toUpperCase(Locale.ROOT);
    }

    private static String slugify(String input) {
        if (input == null || input.isBlank()) return "proj";

        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");

        return normalized
                .toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z0-9]", "") // remove tudo que não for alfanumérico
                .substring(0, Math.min(6, normalized.length())) // limita tamanho
                .replaceAll("^$", "proj"); // fallback se vazio
    }

    private static String randomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(ALPHANUMERIC.charAt(RANDOM.nextInt(ALPHANUMERIC.length())));
        }
        return sb.toString();
    }
}