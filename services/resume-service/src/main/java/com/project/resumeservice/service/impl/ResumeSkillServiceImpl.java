package com.project.resumeservice.service.impl;

import com.project.common.dto.request.AddResumeSkillRequest;
import com.project.common.dto.response.ResumeSkillResponse;
import com.project.resumeservice.repository.ResumeSkillRepository;
import com.project.resumeservice.service.ResumeService;
import com.project.resumeservice.service.ResumeSkillService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResumeSkillServiceImpl implements ResumeSkillService {

    private final ResumeSkillRepository resumeSkillRepository;
    private final ResumeService resumeService;

    @Override
    public ResumeSkillResponse addSkill(Long resumeId, Long candidateId, AddResumeSkillRequest req) {
        return null;
    }

    @Override
    public List<ResumeSkillResponse> getSkills(Long resumeId) {
        return List.of();
    }

    @Override
    public ResumeSkillResponse updateSkill(Long skillId, Long resumeId, Long candidateId, AddResumeSkillRequest req) {
        return null;
    }

    @Override
    public void deleteSkill(Long skillId, Long resumeId, Long candidateId) {

    }
}
