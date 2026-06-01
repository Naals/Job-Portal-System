package com.project.resumeservice.mapper;

import com.project.common.dto.PersonalInfoResponse;
import com.project.common.dto.response.ResumeResponse;
import com.project.resumeservice.modal.PersonalInfo;
import com.project.resumeservice.modal.Resume;
import com.project.resumeservice.payload.CreateResumeRequest;
import org.springframework.stereotype.Component;

@Component
public class ResumeMapper {
    private ResumeMapper() {
    }


    public static ResumeResponse toResponse(Resume resume) {
        if (resume == null) return null;

        return ResumeResponse.builder()
                .id(resume.getId())
                .candidateId(resume.getCandidateId())
                .title(resume.getTitle())
                .template(resume.getTemplate())
                .personalInfoResponse(toPersonalInfoResponse(resume.getPersonalInfo()))
                .visibility(resume.getVisibility())
                .isDefault(resume.getIsDefault())
                .summary(resume.getSummary())
                .completionScore(resume.getCompletionScore())
                .createdAt(resume.getCreatedAt())
                .updatedAt(resume.getUpdatedAt())
                .build();
    }

    public static PersonalInfoResponse toPersonalInfoResponse(PersonalInfo personalInfo) {
        if (personalInfo == null) {
            return null;
        }

        return PersonalInfoResponse.builder()
                .firstName(personalInfo.getFirstName())
                .lastName(personalInfo.getLastName())
                .email(personalInfo.getEmail())
                .phone(personalInfo.getPhone())
                .headline(personalInfo.getHeadline())
                .country(personalInfo.getCountry())
                .city(personalInfo.getCity())
                .githubUrl(personalInfo.getGithubUrl())
                .linkedInUrl(personalInfo.getLinkedInUrl())
                .portfolioUrl(personalInfo.getPortfolioUrl())
                .websiteUrl(personalInfo.getWebsiteUrl())
                .build();
    }

    public static Resume toEntity(CreateResumeRequest request, Long candidateId) {
        if (request == null) return null;

        Resume.ResumeBuilder builder = Resume.builder()
                .candidateId(candidateId)
                .title(request.getTitle());

        if (request.getTemplate() != null) builder.template(request.getTemplate());
        if (request.getVisibility() != null) builder.visibility(request.getVisibility());
        if (request.getIsDefault() != null) builder.isDefault(request.getIsDefault());

        return builder.build();
    }
}
