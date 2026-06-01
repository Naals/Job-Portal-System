package com.project.resumeservice.service;

import com.project.common.dto.response.ResumeResponse;
import com.project.resumeservice.modal.PersonalInfo;
import com.project.resumeservice.modal.Resume;
import com.project.resumeservice.payload.CreateResumeRequest;

import java.util.List;

public interface ResumeService {

    ResumeResponse createResume(Long candidateId, CreateResumeRequest req);

    ResumeResponse getResumeById(Long resumeId, Long candidateId);

    List<ResumeResponse> getMyResumes(Long candidateId);

    ResumeResponse updatePersonalInfo(Long resumeId, Long candidateId, PersonalInfo req);

    ResumeResponse updateSummary(Long resumeId, Long candidateId, String summary);

    ResumeResponse setDefaultResume(Long resumeId, Long candidateId);

    void deleteResume(Long resumeId, Long candidateId);

    Resume getResumeEntity(Long resumeId);
}
