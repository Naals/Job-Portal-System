package com.project.userservice.service.impl;

import com.project.common.domain.UserRole;
import com.project.userservice.exception.EmailAlreadyExistsException;
import com.project.userservice.exception.UserRoleException;
import com.project.userservice.mapper.UserMapper;
import com.project.userservice.modal.User;
import com.project.userservice.payload.AuthResponse;
import com.project.userservice.payload.LoginRequest;
import com.project.userservice.payload.SignupRequest;
import com.project.userservice.repository.UserRepository;
import com.project.userservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    @Override
    public AuthResponse signup(SignupRequest request) {

        if(userRepository.existsByEmail(request.getEmail())){
            throw new EmailAlreadyExistsException("Email Already registered : " + request.getEmail());
        }

        if(request.getRole() == UserRole.ROLE_ADMIN){
            throw new UserRoleException("cannot self-register as role admin");
        }

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(request.getPassword())
                .role(request.getRole())
                .phone(request.getPhone())
                .lastLogin(LocalDateTime.now())
                .build();

        User savedUser = userRepository.save(user);

        AuthResponse res = new AuthResponse();
        res.setTitle("welcome " + savedUser.getFullName());
        res.setMessage("Registered Successfully");
        res.setJwt("jwt");
        res.setUser(UserMapper.toDTO(user));

        return res;
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        return null;
    }
}
