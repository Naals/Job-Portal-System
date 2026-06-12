package com.project.resumeservice.service.impl;


import com.project.common.dto.response.EducationResponse;
import com.project.resumeservice.modal.Education;
import com.project.resumeservice.modal.Resume;
import com.project.resumeservice.exception.ResourceNotFoundException;
import com.project.resumeservice.exception.UnauthorizedAccessException;
import com.project.resumeservice.mapper.EducationMapper;
import com.project.resumeservice.payload.AddEducationRequest;
import com.project.resumeservice.repository.EducationRepository;
import com.project.resumeservice.service.ResumeService;
import com.project.resumeservice.service.EducationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EducationServiceImpl implements EducationService {

    private final EducationRepository educationRepository;
    private final ResumeService resumeService;
    private final EducationMapper educationMapper;

    @Override
    @Transactional
    public EducationResponse addEducation(Long resumeId, Long candidateId, AddEducationRequest req) {
        log.info("Adding education records to resumeId: {}", resumeId);
        Resume resume = fetchAndValidateResume(resumeId, candidateId);

        Education education = educationMapper.toEntity(req, resume);
        Education savedEdu = educationRepository.save(education);
        return educationMapper.toResponse(savedEdu);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EducationResponse> getEducations(Long resumeId) {
        log.info("Fetching education blocks for resumeId: {}", resumeId);
        return educationRepository.findByResumeIdOrderByDisplayOrderAsc(resumeId)
                .stream()
                .map(educationMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EducationResponse updateEducation(Long educationId, Long resumeId, Long candidateId, AddEducationRequest req) {
        log.info("Updating educationId: {} for resumeId: {}", educationId, resumeId);
        fetchAndValidateResume(resumeId, candidateId);

        Education existingEdu = educationRepository.findById(educationId)
                .orElseThrow(() -> new ResourceNotFoundException("Education history node not found with id: " + educationId));

        if (!existingEdu.getResume().getId().equals(resumeId)) {
            throw new UnauthorizedAccessException("Education record does not belong to the specified resume");
        }

        educationMapper.updateEntityFromRequest(req, existingEdu);
        Education updatedEdu = educationRepository.save(existingEdu);
        return educationMapper.toResponse(updatedEdu);
    }

    @Override
    @Transactional
    public void deleteEducation(Long educationId, Long resumeId, Long candidateId) {
        log.info("Removing educationId: {} from resumeId: {}", educationId, resumeId);
        fetchAndValidateResume(resumeId, candidateId);

        Education existingEdu = educationRepository.findById(educationId)
                .orElseThrow(() -> new ResourceNotFoundException("Education history node not found with id: " + educationId));

        if (!existingEdu.getResume().getId().equals(resumeId)) {
            throw new UnauthorizedAccessException("Education record does not belong to the specified resume");
        }

        educationRepository.delete(existingEdu);
    }

    private Resume fetchAndValidateResume(Long resumeId, Long candidateId) {
        Resume resume = resumeService.getResumeEntity(resumeId);
        if (resume == null) {
            throw new ResourceNotFoundException("Resume not found with id: " + resumeId);
        }
        if (!resume.getCandidateId().equals(candidateId)) {
            throw new UnauthorizedAccessException("Unauthorized access: Candidate cross-ownership mismatch");
        }
        return resume;
    }
}
