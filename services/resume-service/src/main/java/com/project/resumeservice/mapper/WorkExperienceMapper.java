package com.project.resumeservice.mapper;

import com.project.common.dto.response.WorkExperienceResponse;
import com.project.resumeservice.modal.Resume;
import com.project.resumeservice.modal.WorkExperience;
import com.project.resumeservice.payload.AddWorkExperience;

public final class WorkExperienceMapper {

    private WorkExperienceMapper() {
    }

    public static WorkExperience toEntity(AddWorkExperience req, Resume resume) {
        if (req == null) {
            return null;
        }

        return WorkExperience.builder()
                .resume(resume)
                .companyName(req.getCompanyName())
                .companyLogoUrl(req.getCompanyLogoUrl())
                .jobTitle(req.getJobTitle())
                .employmentType(req.getEmploymentType())
                .location(req.getLocation())
                .startDate(req.getStartDate())
                .endDate(req.getEndDate())
                .isCurrentJob(Boolean.TRUE.equals(req.getIsCurrentJob()))
                .description(req.getDescription())
                .technologies(req.getTechnologies())
                .displayOrder(req.getDisplayOrder() != null ? req.getDisplayOrder() : 0)
                .build();
    }

    public static WorkExperienceResponse toResponse(WorkExperience entity) {
        if (entity == null) {
            return null;
        }

        return WorkExperienceResponse.builder()
                .id(entity.getId())
                .companyName(entity.getCompanyName())
                .companyLogoUrl(entity.getCompanyLogoUrl())
                .jobTitle(entity.getJobTitle())
                .employmentType(entity.getEmploymentType())
                .location(entity.getLocation())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .isCurrentJob(entity.getIsCurrentJob())
                .description(entity.getDescription())
                .technologies(entity.getTechnologies())
                .displayOrder(entity.getDisplayOrder())
                .build();
    }
}