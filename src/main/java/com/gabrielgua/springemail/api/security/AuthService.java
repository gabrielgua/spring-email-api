package com.gabrielgua.springemail.api.security;

import com.gabrielgua.springemail.domain.entity.User;
import com.gabrielgua.springemail.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserService userService;

    private AuthResponse toModel(String token) {
        return AuthResponse.builder().token(token).build();
    }

    public AuthResponse register(User user) {
        var token = tokenService.generateToken(user, defaultClaims(user));
        return toModel(token);
    }

    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        var user = userService.findByEmail(request.getEmail());
        var token = tokenService.generateToken(user, defaultClaims(user));
        return toModel(token);
    }


    private Map<String, Object> defaultClaims(User user) {
        //empty for now mb will add more to it later, prob not tho
        return new HashMap<String, Object>() {};
    }

}
