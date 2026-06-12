
package com.project.resumeservice.mapper;

import com.project.common.dto.request.AddResumeSkillRequest;
import com.project.common.dto.response.ResumeSkillResponse;
import com.project.resumeservice.modal.Resume;
import com.project.resumeservice.modal.ResumeSkill;
import org.springframework.stereotype.Component;

@Component
public class ResumeSkillMapper {

    public ResumeSkill toEntity(AddResumeSkillRequest req, Resume resume) {
        if (req == null) {
            return null;
        }

        return ResumeSkill.builder()
                .resume(resume)
                .skillName(req.getSkillName())
                .proficiencyLevel(req.getProficiencyLevel())
                .yearsOfExperience(req.getYearsOfExperience())
                .displayOrder(req.getDisplayOrder() != null ? req.getDisplayOrder() : 0)
                .build();
    }

    public ResumeSkillResponse toResponse(ResumeSkill skill) {
        if (skill == null) {
            return null;
        }

        return ResumeSkillResponse.builder()
                .id(skill.getId())
                .skillName(skill.getSkillName())
                .proficiencyLevel(skill.getProficiencyLevel())
                .yearsOfExperience(skill.getYearsOfExperience())
                .displayOrder(skill.getDisplayOrder())
                .build();
    }

    public void updateEntityFromRequest(AddResumeSkillRequest req, ResumeSkill existingSkill) {
        if (req == null || existingSkill == null) {
            return;
        }

        existingSkill.setSkillName(req.getSkillName());
        existingSkill.setProficiencyLevel(req.getProficiencyLevel());
        existingSkill.setYearsOfExperience(req.getYearsOfExperience());
        
        if (req.getDisplayOrder() != null) {
            existingSkill.setDisplayOrder(req.getDisplayOrder());
        }
    }
}