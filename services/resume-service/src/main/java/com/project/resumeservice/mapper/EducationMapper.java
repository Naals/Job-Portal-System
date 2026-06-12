package com.project.resumeservice.mapper;

import com.project.common.dto.response.EducationResponse;
import com.project.resumeservice.modal.Education;
import com.project.resumeservice.modal.Resume;
import com.project.resumeservice.payload.AddEducationRequest;
import org.springframework.stereotype.Component;

@Component
public class EducationMapper {

    public Education toEntity(AddEducationRequest req, Resume resume) {
        if (req == null) return null;

        return Education.builder()
                .resume(resume)
                .institutionName(req.getInstitutionName())
                .degree(req.getDegree())
                .fieldOfStudy(req.getFieldOfStudy())
                .grade(req.getGrade())
                .startDate(req.getStartDate())
                .endDate(req.getEndDate())
                .isCurrentlyStudying(req.getIsCurrentlyStudying() != null ? req.getIsCurrentlyStudying() : false)
                .description(req.getDescription())
                .displayOrder(req.getDisplayOrder() != null ? req.getDisplayOrder() : 0)
                .build();
    }

    public EducationResponse toResponse(Education edu) {
        if (edu == null) return null;

        return EducationResponse.builder()
                .id(edu.getId())
                .institutionName(edu.getInstitutionName())
                .degree(edu.getDegree())
                .fieldOfStudy(edu.getFieldOfStudy())
                .grade(edu.getGrade())
                .startDate(edu.getStartDate())
                .endDate(edu.getEndDate())
                .isCurrentlyStudying(edu.getIsCurrentlyStudying())
                .description(edu.getDescription())
                .displayOrder(edu.getDisplayOrder())
                .build();
    }

    public void updateEntityFromRequest(AddEducationRequest req, Education existingEdu) {
        if (req == null || existingEdu == null) return;

        existingEdu.setInstitutionName(req.getInstitutionName());
        existingEdu.setDegree(req.getDegree());
        existingEdu.setFieldOfStudy(req.getFieldOfStudy());
        existingEdu.setGrade(req.getGrade());
        existingEdu.setStartDate(req.getStartDate());
        existingEdu.setEndDate(req.getEndDate());
        existingEdu.setIsCurrentlyStudying(req.getIsCurrentlyStudying() != null ? req.getIsCurrentlyStudying() : false);
        existingEdu.setDescription(req.getDescription());

        if (req.getDisplayOrder() != null) {
            existingEdu.setDisplayOrder(req.getDisplayOrder());
        }
    }
}
