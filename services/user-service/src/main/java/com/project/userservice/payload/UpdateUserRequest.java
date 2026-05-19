package com.project.userservice.payload;

import lombok.Data;

@Data
public class UpdateUserRequest {

    private String fullName;
    private String phoneNumber;
    private String profileImageUrl;
}
