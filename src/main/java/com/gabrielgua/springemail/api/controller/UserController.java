package com.gabrielgua.springemail.api.controller;

import com.gabrielgua.springemail.api.model.dtos.UserResponse;
import com.gabrielgua.springemail.api.model.mapper.UserMapper;
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
    private final UserMapper userMapper;

    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userMapper.toResponseList(userService.ListAll());
    }

    @GetMapping("/{userId}")
    public UserResponse getUserById(@PathVariable String userId) {
        return userMapper.toResponse(userService.findById(userId));
    }
}
