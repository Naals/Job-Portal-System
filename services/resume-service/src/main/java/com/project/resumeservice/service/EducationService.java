package com.project.resumeservice.service;


import com.project.common.dto.response.EducationResponse;
import com.project.resumeservice.payload.AddEducationRequest;

import java.util.List;

public interface EducationService {

    EducationResponse addEducation(Long resumeId, Long candidateId, AddEducationRequest req);

    List<EducationResponse> getEducations(Long resumeId);

    EducationResponse updateEducation(Long educationId, Long resumeId, Long candidateId, AddEducationRequest req);

    void deleteEducation(Long educationId, Long resumeId, Long candidateId);
}