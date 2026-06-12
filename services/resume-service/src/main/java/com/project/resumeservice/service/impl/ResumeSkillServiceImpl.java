package com.project.resumeservice.service.impl;

import com.project.common.dto.request.AddResumeSkillRequest;
import com.project.common.dto.response.ResumeSkillResponse;
import com.project.resumeservice.modal.Resume;
import com.project.resumeservice.modal.ResumeSkill;
import com.project.resumeservice.exception.ResourceNotFoundException;
import com.project.resumeservice.exception.UnauthorizedAccessException;
import com.project.resumeservice.mapper.ResumeSkillMapper;
import com.project.resumeservice.repository.ResumeSkillRepository;
import com.project.resumeservice.service.ResumeService;
import com.project.resumeservice.service.ResumeSkillService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResumeSkillServiceImpl implements ResumeSkillService {

    private final ResumeSkillRepository resumeSkillRepository;
    private final ResumeService resumeService;
    private final ResumeSkillMapper resumeSkillMapper;

    @Override
    @Transactional
    public ResumeSkillResponse addSkill(Long resumeId, Long candidateId, AddResumeSkillRequest req) {
        log.info("Adding skill to resumeId: {} for candidateId: {}", resumeId, candidateId);

        Resume resume = fetchAndValidateResume(resumeId, candidateId);
        ResumeSkill resumeSkill = resumeSkillMapper.toEntity(req, resume);
        ResumeSkill savedSkill = resumeSkillRepository.save(resumeSkill);

        return resumeSkillMapper.toResponse(savedSkill);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResumeSkillResponse> getSkills(Long resumeId) {
        log.info("Fetching all skills for resumeId: {}", resumeId);

        return resumeSkillRepository.findByResume_IdOrderByDisplayOrderAsc(resumeId)
                .stream()
                .map(resumeSkillMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ResumeSkillResponse updateSkill(Long skillId, Long resumeId, Long candidateId, AddResumeSkillRequest req) {
        log.info("Updating skillId: {} for resumeId: {}", skillId, resumeId);

        fetchAndValidateResume(resumeId, candidateId);

        ResumeSkill existingSkill = resumeSkillRepository.findById(skillId)
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found with id: " + skillId));

        if (!existingSkill.getResume().getId().equals(resumeId)) {
            throw new UnauthorizedAccessException("Skill does not belong to the specified resume");
        }

        resumeSkillMapper.updateEntityFromRequest(req, existingSkill);
        ResumeSkill updatedSkill = resumeSkillRepository.save(existingSkill);

        return resumeSkillMapper.toResponse(updatedSkill);
    }

    @Override
    @Transactional
    public void deleteSkill(Long skillId, Long resumeId, Long candidateId) {
        log.info("Deleting skillId: {} from resumeId: {}", skillId, resumeId);

        fetchAndValidateResume(resumeId, candidateId);

        ResumeSkill existingSkill = resumeSkillRepository.findById(skillId)
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found with id: " + skillId));

        if (!existingSkill.getResume().getId().equals(resumeId)) {
            throw new UnauthorizedAccessException("Skill does not belong to the specified resume");
        }

        resumeSkillRepository.delete(existingSkill);
    }

    private Resume fetchAndValidateResume(Long resumeId, Long candidateId) {
        Resume resume = resumeService.getResumeEntity(resumeId);

        if (resume == null) {
            throw new ResourceNotFoundException("Resume not found with id: " + resumeId);
        }

        if (!resume.getCandidateId().equals(candidateId)) {
            throw new UnauthorizedAccessException("Unauthorized access: Candidate does not own this resume");
        }
        return resume;
    }
}