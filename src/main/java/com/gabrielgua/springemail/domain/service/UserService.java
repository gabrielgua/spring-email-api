package com.gabrielgua.springemail.domain.service;

import com.gabrielgua.springemail.domain.entity.User;
import com.gabrielgua.springemail.domain.exception.UserNotFoundException;
import com.gabrielgua.springemail.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> ListAll() {
        return userRepository.findAll();
    }

    public User findById(String userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }

    public User save(User user) {
        //TODO: add check for no duped email

        if (user.isNew()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setCreatedAt(Instant.now());
        }

        return userRepository.save(user);
    }
}
