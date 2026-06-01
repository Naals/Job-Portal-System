package com.project.resumeservice.service.impl;

import com.project.common.dto.response.ResumeResponse;
import com.project.resumeservice.exception.ResourceNotFoundException;
import com.project.resumeservice.exception.ResumeAccessDeniedException;
import com.project.resumeservice.mapper.ResumeMapper;
import com.project.resumeservice.modal.PersonalInfo;
import com.project.resumeservice.modal.Resume;
import com.project.resumeservice.payload.CreateResumeRequest;
import com.project.resumeservice.repository.ResumeRepository;
import com.project.resumeservice.service.ResumeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {

    private final ResumeRepository resumeRepository;

    @Override
    @Transactional
    public ResumeResponse createResume(Long candidateId, CreateResumeRequest req) {
        log.info("Creating new resume titled '{}' for candidate ID: {}", req.getTitle(), candidateId);

        Resume resume = ResumeMapper.toEntity(req, candidateId);

        // If this new resume is explicitly set as default, clear the existing default first
        if (Boolean.TRUE.equals(resume.getIsDefault())) {
            resetCurrentDefault(candidateId);
        }

        Resume savedResume = resumeRepository.save(resume);
        return ResumeMapper.toResponse(savedResume);
    }

    @Override
    @Transactional(readOnly = true)
    public ResumeResponse getResumeById(Long resumeId, Long candidateId) {
        log.info("Fetching resume ID: {} for candidate ID: {}", resumeId, candidateId);

        Resume resume = getResumeEntity(resumeId);
        validateOwnership(resume, candidateId);

        return ResumeMapper.toResponse(resume);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResumeResponse> getMyResumes(Long candidateId) {
        log.info("Fetching all resumes belonging to candidate ID: {}", candidateId);

        List<Resume> resumes = resumeRepository.findByCandidateId(candidateId);
        return resumes.stream().map(ResumeMapper::toResponse).toList();
    }


    @Override
    @Transactional
    public ResumeResponse updatePersonalInfo(Long resumeId, Long candidateId, PersonalInfo req) {
        log.info("Updating personal info on resume ID: {} for candidate ID: {}", resumeId, candidateId);

        Resume resume = getResumeEntity(resumeId);
        validateOwnership(resume, candidateId);

        resume.setPersonalInfo(req);
        Resume updatedResume = resumeRepository.save(resume);

        return ResumeMapper.toResponse(updatedResume);
    }

    @Override
    @Transactional
    public ResumeResponse updateSummary(Long resumeId, Long candidateId, String summary) {
        log.info("Updating profile summary on resume ID: {} for candidate ID: {}", resumeId, candidateId);

        Resume resume = getResumeEntity(resumeId);
        validateOwnership(resume, candidateId);

        resume.setSummary(summary);
        Resume updatedResume = resumeRepository.save(resume);

        return ResumeMapper.toResponse(updatedResume);
    }

    @Override
    @Transactional
    public ResumeResponse setDefaultResume(Long resumeId, Long candidateId) {
        log.info("Setting resume ID: {} as default for candidate ID: {}", resumeId, candidateId);

        Resume resume = getResumeEntity(resumeId);
        validateOwnership(resume, candidateId);

        if (Boolean.TRUE.equals(resume.getIsDefault())) {
            return ResumeMapper.toResponse(resume);
        }

        resetCurrentDefault(candidateId);
        resume.setIsDefault(true);

        Resume updatedResume = resumeRepository.save(resume);
        return ResumeMapper.toResponse(updatedResume);
    }

    @Override
    @Transactional
    public void deleteResume(Long resumeId, Long candidateId) {
        log.info("Deleting resume ID: {} for candidate ID: {}", resumeId, candidateId);

        Resume resume = getResumeEntity(resumeId);
        validateOwnership(resume, candidateId);

        resumeRepository.delete(resume);
    }

    @Override
    @Transactional(readOnly = true)
    public Resume getResumeEntity(Long resumeId) {
        return resumeRepository.findById(resumeId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Resume with id: %s not found",resumeId)));
    }


    private void validateOwnership(Resume resume, Long candidateId) {
        if (!resume.getCandidateId().equals(candidateId)) {
            log.error("Security violation: Candidate {} tried accessing Resume owned by Candidate {}", candidateId, resume.getCandidateId());
            throw new ResumeAccessDeniedException(candidateId, resume.getId());
        }
    }

    private void resetCurrentDefault(Long candidateId) {
        resumeRepository.findByCandidateIdAndIsDefaultTrue(candidateId)
                .ifPresent(oldDefault -> {
                    log.debug("Unsetting existing default resume (ID: {}) for candidate", oldDefault.getId());
                    oldDefault.setIsDefault(false);
                    resumeRepository.save(oldDefault);
                });
    }
}
