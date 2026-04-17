package com.gabrielgua.springemail.api.security;

import com.gabrielgua.springemail.api.model.ValidateTokenRequest;
import com.gabrielgua.springemail.api.model.dtos.UserRequest;
import com.gabrielgua.springemail.api.model.dtos.UserResponse;
import com.gabrielgua.springemail.api.model.mapper.UserMapper;
import com.gabrielgua.springemail.domain.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final TokenService tokenService;
    private final UserMapper userMapper;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(@Valid @RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody UserRequest request) {
        var user = userMapper.toEntity(request);
        return ResponseEntity.ok(authService.register(userService.save(user)));
    }

    @PostMapping("/validate-token")
    public Boolean validateToken(@Valid @RequestBody ValidateTokenRequest request) {
        var user = userService.findById(request.getUserId());
        return tokenService.isTokenValid(request.getToken(), request.getUserId());
    }
}
