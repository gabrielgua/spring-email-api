package com.gabrielgua.springemail.api.model.mapper;

import com.gabrielgua.springemail.api.model.dtos.UserRequest;
import com.gabrielgua.springemail.api.model.dtos.UserResponse;
import com.gabrielgua.springemail.domain.entity.User;
import com.gabrielgua.springemail.domain.entity.UserRole;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .role(user.getRole())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .projectIds(user.getProjectIds())
                .build();
    }

    public List<UserResponse> toResponseList(List<User> users) {
        return users.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public User toEntity(UserRequest request) {
        var user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(UserRole.ROLE_USER);
        return user;
    }
}
