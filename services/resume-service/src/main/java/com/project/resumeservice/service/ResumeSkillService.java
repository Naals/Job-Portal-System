package com.project.resumeservice.service;

import com.project.common.dto.request.AddResumeSkillRequest;
import com.project.common.dto.response.ResumeSkillResponse;

import java.util.List;

public interface ResumeSkillService {

    ResumeSkillResponse addSkill(Long resumeId, Long candidateId, AddResumeSkillRequest req);

    List<ResumeSkillResponse> getSkills(Long resumeId);

    ResumeSkillResponse updateSkill(Long skillId, Long resumeId, Long candidateId, AddResumeSkillRequest req);

    void deleteSkill(Long skillId, Long resumeId, Long candidateId);
}
