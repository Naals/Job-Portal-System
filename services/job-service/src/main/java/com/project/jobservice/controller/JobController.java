package com.project.jobservice.controller;


import com.project.common.dto.request.JobRequest;
import com.project.common.dto.response.JobResponse;
import com.project.jobservice.payload.JobSearchRequest;
import com.project.jobservice.service.JobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @PostMapping
    public ResponseEntity<JobResponse> createJob(
            @RequestHeader("X-User-Id") Long employerId,
            @Valid @RequestBody JobRequest request) {
        JobResponse response = jobService.createJob(employerId, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{jobId}/employer}")
    public ResponseEntity<JobResponse> updateJob(
            @PathVariable Long jobId,
            @RequestHeader("X-User-Id") Long employerId,
            @Valid @RequestBody JobRequest request) {
        JobResponse response = jobService.updateJob(jobId, employerId, request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{jobId}/publish/employer")
    public ResponseEntity<JobResponse> publishJob(
            @PathVariable Long jobId,
            @RequestHeader("X-User-Id") Long employerId) {
        JobResponse response = jobService.publishJob(jobId, employerId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{jobId}/close/employer}")
    public ResponseEntity<JobResponse> closeJob(
            @PathVariable Long jobId,
            @RequestHeader("X-User-Id") Long employerId) {
        JobResponse response = jobService.closeJob(jobId, employerId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{jobId}/employer}")
    public ResponseEntity<JobResponse> deleteJob(
            @PathVariable Long jobId,
            @RequestHeader("X-User-Id") Long employerId) {
        JobResponse response = jobService.deleteJob(jobId, employerId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobResponse> getJobById(@PathVariable Long id) {
        JobResponse response = jobService.getJobById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<JobResponse>> getJobs(JobSearchRequest searchRequest) {
        List<JobResponse> responses = jobService.getJobs(searchRequest);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<JobResponse>> getJobsByCompany(@PathVariable Long companyId) {
        List<JobResponse> responses = jobService.getJobsByCompany(companyId);
        return ResponseEntity.ok(responses);
    }


    @GetMapping("/admin")
    public ResponseEntity<List<JobResponse>> getAllJobsAdmin() {
        List<JobResponse> responses = jobService.getAllJobsAdmin();
        return ResponseEntity.ok(responses);
    }
}
