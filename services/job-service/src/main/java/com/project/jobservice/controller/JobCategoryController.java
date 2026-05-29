package com.project.jobservice.controller;

import com.project.common.dto.response.JobCategoryResponse;
import com.project.jobservice.mapper.JobCategoryMapper;
import com.project.jobservice.modal.JobCategory;
import com.project.jobservice.payload.JobCategoryRequest;
import com.project.jobservice.service.JobCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/job-categories")
@RequiredArgsConstructor
public class JobCategoryController {

    private final JobCategoryService service;

    @PostMapping
    public ResponseEntity<JobCategoryResponse> createCategory(@Valid @RequestBody JobCategoryRequest request) {
        JobCategoryResponse response = service.createCategory(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<JobCategoryResponse>> getAllCategories(
            @RequestParam(value = "includeChildren", defaultValue = "true") boolean includeChildren) {

        List<JobCategoryResponse> categories = service.getAllCategories();

        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobCategoryResponse> getCategoryById(
            @PathVariable Long id,
            @RequestParam(value = "includeChildren", defaultValue = "true") boolean includeChildren) {

        JobCategory categoryEntity = service.getCategoryEntityById(id);
        JobCategoryResponse response = JobCategoryMapper.toResponse(categoryEntity, includeChildren);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobCategoryResponse> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody JobCategoryRequest request) {

        JobCategoryResponse response = service.updateCategory(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        service.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
