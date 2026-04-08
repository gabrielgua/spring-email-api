package com.gabrielgua.springemail.domain.service;

import com.gabrielgua.springemail.domain.entity.User;
import com.gabrielgua.springemail.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> ListAll() {
        return userRepository.findAll();
    }

    public User getUserById(String userId) {
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found"));
    }

    public void save(User user) {
        userRepository.save(user);
    }
}
