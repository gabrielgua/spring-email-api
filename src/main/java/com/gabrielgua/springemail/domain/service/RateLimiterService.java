package com.gabrielgua.springemail.domain.service;

import com.gabrielgua.springemail.infra.properties.EmailRateLimiterProperties;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class RateLimiterService {

    private final EmailRateLimiterProperties properties;
    private final Map<String, RateLimiter> rateLimiters = new ConcurrentHashMap<>();

    public boolean tryConsume(String key) {
        RateLimiter limiter = rateLimiters.computeIfAbsent(key, k ->
                RateLimiter.of(k,
                        RateLimiterConfig.custom()
                                .limitForPeriod(properties.getLimitForPeriod())
                                .limitRefreshPeriod(properties.getLimitRefreshPeriod())
                                .timeoutDuration(properties.getTimeoutDuration())
                                .build()
                )
        );

        return limiter.acquirePermission();
    }
}
