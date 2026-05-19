package com.project.userservice.controller;

import com.project.userservice.payload.*;
import com.project.userservice.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(
            @RequestBody @Valid SignupRequest req
    ) {
        return ResponseEntity.ok(authService.signup(req));
    }

}
