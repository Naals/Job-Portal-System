package com.project.userservice.payload;

import com.project.common.domain.UserRole;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class SignupRequest {

    @NotBlank(message = "full name is mandatory")
    private String fullName;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is mandatory")
    private String email;

    @NotBlank(message = "Password is mandatory")
    private String password;

    private String phone;

    @NotNull(message = "Role is mandatory")
    private UserRole role;

}
