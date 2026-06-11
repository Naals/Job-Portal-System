package com.project.resumeservice.service;

import com.project.common.dto.response.WorkExperienceResponse;
import com.project.resumeservice.modal.WorkExperience;
import com.project.resumeservice.payload.AddWorkExperience;

import java.util.List;

public interface WorkExperienceService {

    WorkExperienceResponse addWorkExperience(Long resumeId, Long candidateId, AddWorkExperience req);

    List<WorkExperienceResponse> getWorkExperiences(Long resumeId);

    WorkExperienceResponse updateWorkExperience(Long resumeId, Long candidateId, Long workExperienceId, AddWorkExperience req);

    void deleteWorkExperience(Long resumeId, Long workExperienceId, Long candidateId);

    WorkExperience getWorkExperience(Long workExperienceId);
}