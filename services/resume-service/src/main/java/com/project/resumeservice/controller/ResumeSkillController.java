package com.project.resumeservice.controller;

import com.project.common.dto.request.AddResumeSkillRequest;
import com.project.common.dto.response.ResumeSkillResponse;
import com.project.resumeservice.service.ResumeSkillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/resumes/{resumeId}/skills")
@RequiredArgsConstructor
public class ResumeSkillController {

    private final ResumeSkillService resumeSkillService;

    @PostMapping
    public ResponseEntity<ResumeSkillResponse> addSkill(
            @PathVariable Long resumeId,
            @RequestHeader("X-User-Id") Long candidateId,
            @Valid @RequestBody AddResumeSkillRequest request) {

        log.info("REST request to add skill to resumeId: {} for candidateId: {}", resumeId, candidateId);
        ResumeSkillResponse response = resumeSkillService.addSkill(resumeId, candidateId, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ResumeSkillResponse>> getSkills(@PathVariable Long resumeId) {
        log.info("REST request to fetch all skills for resumeId: {}", resumeId);
        List<ResumeSkillResponse> responses = resumeSkillService.getSkills(resumeId);
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{skillId}")
    public ResponseEntity<ResumeSkillResponse> updateSkill(
            @PathVariable Long resumeId,
            @PathVariable Long skillId,
            @RequestHeader("X-User-Id") Long candidateId,
            @Valid @RequestBody AddResumeSkillRequest request) {

        log.info("REST request to update skillId: {} for resumeId: {}", skillId, resumeId);
        ResumeSkillResponse response = resumeSkillService.updateSkill(skillId, resumeId, candidateId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{skillId}")
    public ResponseEntity<Void> deleteSkill(
            @PathVariable Long resumeId,
            @PathVariable Long skillId,
            @RequestHeader("X-User-Id") Long candidateId) {

        log.info("REST request to delete skillId: {} from resumeId: {}", skillId, resumeId);
        resumeSkillService.deleteSkill(skillId, resumeId, candidateId);
        return ResponseEntity.noContent().build();
    }
}