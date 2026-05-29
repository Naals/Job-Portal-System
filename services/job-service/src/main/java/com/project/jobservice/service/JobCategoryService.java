package com.project.jobservice.service;

import com.project.common.dto.response.JobCategoryResponse;
import com.project.jobservice.modal.JobCategory;
import com.project.jobservice.payload.JobCategoryRequest;

import java.util.List;

public interface JobCategoryService {

    JobCategoryResponse createCategory(JobCategoryRequest req);

    List<JobCategoryResponse> getAllCategories();

    JobCategoryResponse getCategoryById(Long id);

    JobCategoryResponse updateCategory(Long id, JobCategoryRequest req);

    void deleteCategory(Long id);

    JobCategory getCategoryEntityById(Long id);
}
