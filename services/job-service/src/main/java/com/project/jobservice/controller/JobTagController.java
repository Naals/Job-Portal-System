package com.project.jobservice.controller;

import com.project.common.dto.response.ApiResponse;
import com.project.common.dto.response.JobTagResponse;
import com.project.jobservice.payload.JobTagRequest;
import com.project.jobservice.service.JobTagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/job-tags")
@RequiredArgsConstructor
public class JobTagController {

    private final JobTagService jobTagService;


    @PostMapping
    public ResponseEntity<JobTagResponse> createTag(@Valid @RequestBody JobTagRequest request) {
        log.info("REST request to create Job Tag: {}", request.getName());
        JobTagResponse createdTag = jobTagService.createTag(request);
        return new ResponseEntity<>(createdTag, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<JobTagResponse>> getAllTags() {
        log.info("REST request to get all Job Tags");
        List<JobTagResponse> tags = jobTagService.getAllTags();
        return ResponseEntity.ok(tags);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobTagResponse> getTagById(@PathVariable Long id) {
        log.info("REST request to get Job Tag with id: {}", id);
        JobTagResponse tag = jobTagService.getById(id);
        return ResponseEntity.ok(tag);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobTagResponse> updateTag(
            @PathVariable Long id,
            @Valid @RequestBody JobTagRequest request) {

        log.info("REST request to update Job Tag with id: {}", id);
        JobTagResponse updatedTag = jobTagService.updateTag(id, request);
        return ResponseEntity.ok(updatedTag);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteTag(@PathVariable Long id) {
        log.info("REST request to delete Job Tag with id: {}", id);
        jobTagService.deleteTag(id);
        return ResponseEntity.ok(new ApiResponse("Job Tag deleted successfully", true));
    }
}