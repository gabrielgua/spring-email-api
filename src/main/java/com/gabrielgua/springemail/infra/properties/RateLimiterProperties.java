package com.gabrielgua.springemail.infra.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Data
@Configuration
@ConfigurationProperties(prefix = "api.ratelimiter")
public class RateLimiterProperties {

    private Email email;

    @Data
    public static class Email {
        private int limitForPeriod;
        private Duration limitRefreshPeriod;
        private Duration timeoutDuration;
    }

}
