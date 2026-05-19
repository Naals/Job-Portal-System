package com.project.userservice.service.impl;

import com.project.common.domain.UserRole;
import com.project.userservice.exception.BadCredentialsException;
import com.project.userservice.exception.EmailAlreadyExistsException;
import com.project.userservice.exception.UserRoleException;
import com.project.userservice.mapper.UserMapper;
import com.project.userservice.modal.User;
import com.project.userservice.payload.AuthResponse;
import com.project.userservice.payload.LoginRequest;
import com.project.userservice.payload.SignupRequest;
import com.project.userservice.repository.UserRepository;
import com.project.userservice.security.CustomUserDetailsService;
import com.project.userservice.security.JwtProvider;
import com.project.userservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomUserDetailsService customUserDetailsService;

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
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .phone(request.getPhone())
                .lastLogin(LocalDateTime.now())
                .build();

        User savedUser = userRepository.save(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user.getEmail(), user.getPassword());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication, savedUser.getId());

        AuthResponse res = new AuthResponse();
        res.setTitle("welcome " + savedUser.getFullName());
        res.setMessage("Registered Successfully");
        res.setJwt(jwt);
        res.setUser(UserMapper.toDTO(user));

        return res;
    }

    @Override
    public AuthResponse login(LoginRequest req) {
        Authentication authentication = authenticate(
                req.getEmail(), req.getPassword()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userRepository.findByEmail(req.getEmail());

        String jwt = jwtProvider.generateToken(authentication, user.getId());

        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        AuthResponse res = new AuthResponse();
        res.setTitle("welcome " + user.getFullName());
        res.setMessage("Login Successfully");
        res.setJwt(jwt);
        res.setUser(UserMapper.toDTO(user));

        return res;
    }

    private Authentication authenticate(String email, String password) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

        if(!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid email or password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }
}
