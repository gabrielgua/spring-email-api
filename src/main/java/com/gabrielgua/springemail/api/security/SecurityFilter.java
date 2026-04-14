package com.gabrielgua.springemail.api.security;

import com.gabrielgua.springemail.domain.repository.UserRepository;
import com.gabrielgua.springemail.domain.service.UserService;
import com.gabrielgua.springemail.exception.OutputResponseHelper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final OutputResponseHelper helper;
    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String token;
        final String userId;
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header == null || !header.startsWith("Bearer ")) {

            System.out.println("Authorization header not found or doesnt starts with Bearer: " + header);
            filterChain.doFilter(request, response);
            return;
        }

        token = header.substring(7);
        try {
            userId = tokenService.getTokenSubject(token);

            if (userId != null && !isAuthenticated()) {
                var user = userRepository.findById(userId);

                if (user.isEmpty()) {
                    helper.tokenInvalid(response);
                    return;
                }

                var userDetails = userDetailsService.loadUserByUsername(user.get().getEmail());
                if (tokenService.isTokenValid(token, user.get().getId())) {
                    var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }

                filterChain.doFilter(request, response);
            }
        } catch (ExpiredJwtException ex) {
            helper.tokenExpired(response);
        } catch (MalformedJwtException | SignatureException ex) {
            helper.tokenInvalid(response);
        }
    }

    private boolean isAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication() != null;
    }
}
