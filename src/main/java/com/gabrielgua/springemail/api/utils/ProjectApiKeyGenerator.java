package com.gabrielgua.springemail.api.utils;

import lombok.AllArgsConstructor;

import java.security.SecureRandom;

@AllArgsConstructor
public class ProjectApiKeyGenerator {

    private static final SecureRandom RANDOM = new SecureRandom();
    private static final String ALPHANUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final Integer RANDOM_LENGTH = 25;

    public static String generate() {
        StringBuilder sb = new StringBuilder("PROJ_");
        for (int i = 0; i < RANDOM_LENGTH; i++) {
            sb.append(ALPHANUMERIC.charAt(RANDOM.nextInt(ALPHANUMERIC.length())));
        }

        return sb.toString();
    }
}
