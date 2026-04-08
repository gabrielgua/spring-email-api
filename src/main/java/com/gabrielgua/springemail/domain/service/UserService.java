package com.gabrielgua.springemail.domain.service;

import com.gabrielgua.springemail.domain.entity.User;
import com.gabrielgua.springemail.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passWordEncoder;

    public List<User> ListAll() {
        return userRepository.findAll();
    }

    public User findById(String userId) {
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found"));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User Not Found"));
    }

    public void save(User user) {
        //TODO: add check for no duped email

        if (user.isNew()) {
            user.setPassword(passWordEncoder.encode(user.getPassword()));
        }
        userRepository.save(user);
    }
}
