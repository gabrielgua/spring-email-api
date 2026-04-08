package com.gabrielgua.springemail.api.security;

import com.gabrielgua.springemail.api.model.ValidateTokenRequest;
import com.gabrielgua.springemail.domain.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final TokenService tokenService;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(@Valid @RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @PostMapping("/validate-token")
    public Boolean validateToken(@Valid @RequestBody ValidateTokenRequest request) {
        var user = userService.findById(request.getUserId());
        return tokenService.isTokenValid(request.getToken(), request.getUserId());
    }
}
