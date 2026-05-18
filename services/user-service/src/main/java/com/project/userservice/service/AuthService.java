package com.project.userservice.service;

import com.project.userservice.payload.AuthResponse;
import com.project.userservice.payload.LoginRequest;
import com.project.userservice.payload.SignupRequest;

public interface AuthService {

    AuthResponse signup(SignupRequest request);
    AuthResponse login(LoginRequest request);
}
