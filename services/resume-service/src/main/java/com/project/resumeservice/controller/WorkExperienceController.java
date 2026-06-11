package com.project.resumeservice.controller;

import com.project.common.dto.response.ApiResponse;
import com.project.common.dto.response.WorkExperienceResponse;
import com.project.resumeservice.payload.AddWorkExperience;
import com.project.resumeservice.service.WorkExperienceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/resumes/{resumeId}/work-experiences")
@RequiredArgsConstructor
public class WorkExperienceController {

    private final WorkExperienceService workExperienceService;

    @PostMapping
    public ResponseEntity<WorkExperienceResponse> addWorkExperience(
            @PathVariable Long resumeId,
            @RequestHeader("X-User-Id") Long candidateId,
            @Valid @RequestBody AddWorkExperience request) {
        log.info("REST request to add work experience to resume ID: {} for candidate ID: {}", resumeId, candidateId);
        WorkExperienceResponse response = workExperienceService.addWorkExperience(resumeId, candidateId, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<WorkExperienceResponse>> getWorkExperiences(
            @PathVariable Long resumeId) {
        log.info("REST request to fetch all work experiences for resume ID: {}", resumeId);
        List<WorkExperienceResponse> responses = workExperienceService.getWorkExperiences(resumeId);
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{workExperienceId}")
    public ResponseEntity<WorkExperienceResponse> updateWorkExperience(
            @PathVariable Long resumeId,
            @PathVariable Long workExperienceId,
            @RequestHeader("X-User-Id") Long candidateId,
            @Valid @RequestBody AddWorkExperience request) {
        log.info("REST request to update work experience ID: {} on resume ID: {} for candidate ID: {}",
                workExperienceId, resumeId, candidateId);
        WorkExperienceResponse response = workExperienceService.updateWorkExperience(resumeId, workExperienceId, candidateId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{workExperienceId}")
    public ResponseEntity<ApiResponse> deleteWorkExperience(
            @PathVariable Long resumeId,
            @PathVariable Long workExperienceId,
            @RequestHeader("X-User-Id") Long candidateId) {
        log.info("REST request to delete work experience ID: {} from resume ID: {} for candidate ID: {}",
                workExperienceId, resumeId, candidateId);
        workExperienceService.deleteWorkExperience(resumeId, workExperienceId, candidateId);
        return ResponseEntity.ok(new ApiResponse(
                "Work Experience deleted successfully", true
        ));
    }
}