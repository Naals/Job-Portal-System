package com.project.userservice.service.impl;

import com.project.common.domain.UserStatus;
import com.project.common.dto.response.UserResponse;
import com.project.userservice.exception.BadCredentialsException;
import com.project.userservice.mapper.UserMapper;
import com.project.userservice.modal.User;
import com.project.userservice.payload.UpdateUserRequest;
import com.project.userservice.repository.UserRepository;
import com.project.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new BadCredentialsException("User not found with email: " + email);
        }
        return user;
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new BadCredentialsException("User not found with id: " + id));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserResponse updateProfile(String email, UpdateUserRequest req) {
        User user = getUserByEmail(email);

        if (req.getFullName() != null) {
            user.setFullName(req.getFullName());
        }
        if (req.getPhoneNumber() != null) {
            user.setPhone(req.getPhoneNumber());
        }
        if (req.getProfileImageUrl() != null) {
            user.setProfileImage(req.getProfileImageUrl());
        }

        User updatedUser = userRepository.save(user);
        return UserMapper.toDTO(updatedUser);
    }

    @Override
    public UserResponse suspendUser(Long id) {
        User user = getUserById(id);
        user.setStatus(UserStatus.SUSPENDED);
        user.setSuspendedAt(LocalDateTime.now());

        User updatedUser = userRepository.save(user);
        return UserMapper.toDTO(updatedUser);
    }

    @Override
    public UserResponse activateUser(Long id) {
        User user = getUserById(id);
        user.setStatus(UserStatus.ACTIVE);
        user.setSuspendedAt(null);

        User updatedUser = userRepository.save(user);
        return UserMapper.toDTO(updatedUser);
    }

    @Override
    public UserResponse deleteUser(Long id) {
        User user = getUserById(id);
        user.setStatus(UserStatus.DELETED);
        user.setDeletedAt(LocalDateTime.now());

        User updatedUser = userRepository.save(user);
        return UserMapper.toDTO(updatedUser);
    }
}
