package com.gabrielgua.springemail.api.controller;

import com.gabrielgua.springemail.domain.entity.User;
import com.gabrielgua.springemail.domain.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.ListAll();
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable String userId) {
        return  userService.getUserById(userId);
    }
}
