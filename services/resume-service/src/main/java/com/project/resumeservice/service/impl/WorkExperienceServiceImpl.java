package com.project.resumeservice.service.impl;

import com.project.common.dto.response.WorkExperienceResponse;
import com.project.resumeservice.exception.ResourceNotFoundException;
import com.project.resumeservice.exception.ResumeAccessDeniedException;
import com.project.resumeservice.mapper.WorkExperienceMapper;
import com.project.resumeservice.modal.Resume;
import com.project.resumeservice.modal.WorkExperience;
import com.project.resumeservice.payload.AddWorkExperience;
import com.project.resumeservice.repository.WorkExperienceRepository;
import com.project.resumeservice.service.ResumeService;
import com.project.resumeservice.service.WorkExperienceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkExperienceServiceImpl implements WorkExperienceService {

    private final WorkExperienceRepository workExperienceRepository;
    private final ResumeService resumeService;

    @Override
    @Transactional
    public WorkExperienceResponse addWorkExperience(Long resumeId, Long candidateId, AddWorkExperience req) {
        log.info("Adding work experience at '{}' to resume ID: {} for candidate ID: {}", req.getCompanyName(), resumeId, candidateId);

        Resume resume = resumeService.getResumeEntity(resumeId);
        validateResumeOwnership(resume, candidateId);

        WorkExperience workExperience = WorkExperienceMapper.toEntity(req, resume);

        WorkExperience savedExperience = workExperienceRepository.save(workExperience);
        return WorkExperienceMapper.toResponse(savedExperience);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkExperienceResponse> getWorkExperiences(Long resumeId) {
        log.info("Fetching all work experiences for resume ID: {}", resumeId);

        resumeService.getResumeEntity(resumeId);

        return workExperienceRepository.findByResumeIdOrderByDisplayOrderAsc(resumeId)
                .stream()
                .map(WorkExperienceMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public WorkExperienceResponse updateWorkExperience(Long resumeId, Long candidateId,  Long workExperienceId, AddWorkExperience req) {
        log.info("Updating work experience ID: {} on resume ID: {} for candidate ID: {}", workExperienceId, resumeId, candidateId);

        Resume resume = resumeService.getResumeEntity(resumeId);
        validateResumeOwnership(resume, candidateId);

        WorkExperience experience = getWorkExperience(workExperienceId);
        validateExperienceBelongsToResume(experience, resumeId);

        experience.setCompanyName(req.getCompanyName());
        experience.setCompanyLogoUrl(req.getCompanyLogoUrl());
        experience.setJobTitle(req.getJobTitle());
        experience.setEmploymentType(req.getEmploymentType());
        experience.setLocation(req.getLocation());
        experience.setStartDate(req.getStartDate());
        experience.setEndDate(req.getEndDate());
        experience.setIsCurrentJob(Boolean.TRUE.equals(req.getIsCurrentJob()));
        experience.setDescription(req.getDescription());
        experience.setTechnologies(req.getTechnologies());
        experience.setDisplayOrder(req.getDisplayOrder() != null ? req.getDisplayOrder() : 0);

        WorkExperience updatedExperience = workExperienceRepository.save(experience);
        return WorkExperienceMapper.toResponse(updatedExperience);
    }

    @Override
    @Transactional
    public void deleteWorkExperience(Long resumeId, Long workExperienceId, Long candidateId) {
        log.info("Deleting work experience ID: {} from resume ID: {} by candidate ID: {}", workExperienceId, resumeId, candidateId);

        Resume resume = resumeService.getResumeEntity(resumeId);
        validateResumeOwnership(resume, candidateId);

        WorkExperience experience = getWorkExperience(workExperienceId);
        validateExperienceBelongsToResume(experience, resumeId);

        workExperienceRepository.delete(experience);
    }

    @Override
    @Transactional(readOnly = true)
    public WorkExperience getWorkExperience(Long workExperienceId) {
        return workExperienceRepository.findById(workExperienceId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Work experience with id: %s not found", workExperienceId)));
    }

    private void validateResumeOwnership(Resume resume, Long candidateId) {
        if (!resume.getCandidateId().equals(candidateId)) {
            log.error("Security violation: Candidate {} tried manipulating Resume owned by Candidate {}", candidateId, resume.getCandidateId());
            throw new ResumeAccessDeniedException(candidateId, resume.getId());
        }
    }

    private void validateExperienceBelongsToResume(WorkExperience experience, Long resumeId) {
        if (!experience.getResume().getId().equals(resumeId)) {
            log.error("Structure violation: WorkExperience ID {} does not belong to Resume ID {}", experience.getId(), resumeId);
            throw new ResourceNotFoundException(
                    String.format("Work experience ID %s is not assigned to Resume ID %s", experience.getId(), resumeId));
        }
    }
}