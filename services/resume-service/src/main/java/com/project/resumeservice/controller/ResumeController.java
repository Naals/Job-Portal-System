package com.project.resumeservice.controller;

import com.project.common.dto.response.ApiResponse;
import com.project.common.dto.response.ResumeResponse;
import com.project.resumeservice.modal.PersonalInfo;
import com.project.resumeservice.payload.CreateResumeRequest;
import com.project.resumeservice.service.ResumeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/resumes")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeService resumeService;

    @PostMapping
    public ResponseEntity<ResumeResponse> createResume(
            @RequestHeader("X-User-Id") Long candidateId,
            @Valid @RequestBody CreateResumeRequest request) {
        log.info("REST request to create resume for candidate: {}", candidateId);
        ResumeResponse response = resumeService.createResume(candidateId, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResumeResponse> getResumeById(
            @PathVariable("id") Long resumeId,
            @RequestHeader("X-User-Id") Long candidateId) {
        log.info("REST request to get resume: {} for candidate: {}", resumeId, candidateId);
        ResumeResponse response = resumeService.getResumeById(resumeId, candidateId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ResumeResponse>> getMyResumes(
            @RequestHeader("X-User-Id") Long candidateId) {
        log.info("REST request to fetch all resumes for candidate: {}", candidateId);
        List<ResumeResponse> responses = resumeService.getMyResumes(candidateId);
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}/personal-info")
    public ResponseEntity<ResumeResponse> updatePersonalInfo(
            @PathVariable("id") Long resumeId,
            @RequestHeader("X-User-Id") Long candidateId,
            @Valid @RequestBody PersonalInfo personalInfo) {
        log.info("REST request to update personal info for resume: {}", resumeId);
        ResumeResponse response = resumeService.updatePersonalInfo(resumeId, candidateId, personalInfo);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/summary")
    public ResponseEntity<ResumeResponse> updateSummary(
            @PathVariable("id") Long resumeId,
            @RequestHeader("X-User-Id") Long candidateId,
            @RequestBody String summary) {
        log.info("REST request to update summary for resume: {}", resumeId);
        ResumeResponse response = resumeService.updateSummary(resumeId, candidateId, summary);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/default")
    public ResponseEntity<ResumeResponse> setDefaultResume(
            @PathVariable("id") Long resumeId,
            @RequestHeader("X-User-Id") Long candidateId) {
        log.info("REST request to set resume: {} as default for candidate: {}", resumeId, candidateId);
        ResumeResponse response = resumeService.setDefaultResume(resumeId, candidateId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteResume(
            @PathVariable("id") Long resumeId,
            @RequestHeader("X-User-Id") Long candidateId) {
        log.info("REST request to delete resume: {} for candidate: {}", resumeId, candidateId);
        resumeService.deleteResume(resumeId, candidateId);
        return ResponseEntity.ok(new ApiResponse("Resume deleted successfully", true));
    }
}