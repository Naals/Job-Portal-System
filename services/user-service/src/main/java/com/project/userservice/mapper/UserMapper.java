package com.project.userservice.mapper;

import com.project.common.dto.response.UserResponse;
import com.project.userservice.modal.User;

import java.util.List;

public final class UserMapper {

    private UserMapper() {
    }

    public static UserResponse toDTO(User user) {

        if (user == null) {
            return null;
        }

        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .phone(user.getPhone())
                .profileImage(user.getProfileImage())
                .role(user.getRole())
                .status(user.getStatus())
                .lastLogin(user.getLastLogin())
                .createdAt(user.getCreatedAt())
                .build();
    }

    public static List<UserResponse> toDTOList(List<User> users) {

        if (users == null) {
            return List.of();
        }

        return users.stream()
                .map(UserMapper::toDTO)
                .toList();
    }
}