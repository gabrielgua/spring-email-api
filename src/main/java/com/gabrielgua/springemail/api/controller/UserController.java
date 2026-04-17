package com.gabrielgua.springemail.api.controller;

import com.gabrielgua.springemail.api.model.dtos.UserRequest;
import com.gabrielgua.springemail.api.model.dtos.UserResponse;
import com.gabrielgua.springemail.api.model.mapper.UserMapper;
import com.gabrielgua.springemail.api.security.CheckSecurity;
import com.gabrielgua.springemail.domain.entity.User;
import com.gabrielgua.springemail.domain.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    @CheckSecurity.General.isAdmin
    public List<UserResponse> getAllUsers() {
        return userMapper.toResponseList(userService.ListAll());
    }

    @GetMapping("/{userId}")
    @CheckSecurity.Users.canManage
    public UserResponse getUserById(@PathVariable String userId) {
        return userMapper.toResponse(userService.findById(userId));
    }
}
