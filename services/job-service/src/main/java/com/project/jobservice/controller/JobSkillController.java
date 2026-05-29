package com.project.jobservice.controller;

import com.project.common.dto.response.ApiResponse;
import com.project.common.dto.response.JobSkillResponse;
import com.project.jobservice.payload.JobSkillRequest;
import com.project.jobservice.service.JobSkillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skills")
@RequiredArgsConstructor
public class JobSkillController {

    private final JobSkillService jobSkillService;

    @PostMapping
    public ResponseEntity<JobSkillResponse> createSkill(@Valid @RequestBody JobSkillRequest request) {
        JobSkillResponse response = jobSkillService.createSkill(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<JobSkillResponse>> getAllSkills() {
        List<JobSkillResponse> responses = jobSkillService.getAllSkills();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobSkillResponse> getSkillById(@PathVariable Long id) {
        JobSkillResponse response = jobSkillService.getSkillById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobSkillResponse> updateSkill(
            @PathVariable Long id,
            @Valid @RequestBody JobSkillRequest request) {
        JobSkillResponse response = jobSkillService.updateSkill(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteSkill(@PathVariable Long id) {
        jobSkillService.deleteSkill(id);
        return ResponseEntity.ok(new ApiResponse("Job Skill DELETED successfully", true));
    }
}