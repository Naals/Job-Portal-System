package com.project.userservice.service;

import com.project.common.dto.response.UserResponse;
import com.project.userservice.modal.User;
import com.project.userservice.payload.UpdateUserRequest;

import java.util.List;

public interface UserService {

    User getUserByEmail(String email);

    User getUserById(Long id);

    List<User> getAllUsers();

    UserResponse updateProfile(String email, UpdateUserRequest req);

    UserResponse suspendUser(Long id);

    UserResponse activateUser(Long id);

    UserResponse deleteUser(Long id);

}
