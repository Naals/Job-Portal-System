package com.project.resumeservice.modal;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class PersonalInfo {

    private String firstName;
    private String lastName;
    private String headline;
    private String email;
    private String phone;
    private String city;
    private String country;
    private String linkedInUrl;
    private String githubUrl;
    private String portfolioUrl;
    private String websiteUrl;
}