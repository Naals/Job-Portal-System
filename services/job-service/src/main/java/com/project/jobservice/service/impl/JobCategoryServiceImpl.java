package com.project.jobservice.service.impl;

import com.project.common.dto.JobCategoryResponse;
import com.project.jobservice.exception.BadRequestException;
import com.project.jobservice.exception.ResourceNotFoundException;
import com.project.jobservice.mapper.JobCategoryMapper;
import com.project.jobservice.modal.JobCategory;
import com.project.jobservice.payload.JobCategoryRequest;
import com.project.jobservice.repository.JobCategoryRepository;
import com.project.jobservice.service.JobCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class JobCategoryServiceImpl implements JobCategoryService {

    private final JobCategoryRepository repository;

    @Override
    @Transactional
    public JobCategoryResponse createCategory(JobCategoryRequest req) {

        if (repository.existsByName(req.getName())) {
            throw new BadRequestException("Category with name '" + req.getName() + "' already exists.");
        }

        String slug = generateSlug(req.getName());
        if (repository.existsBySlug(slug)) {
            throw new BadRequestException("Category slug '" + slug + "' already exists.");
        }


        JobCategory parent = null;

        if (req.getParentId() != null) {
            parent = repository.findById(req.getParentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Parent category not found with id: " + req.getParentId()));
        }
        
        JobCategory category = JobCategory.builder()
                .name(req.getName())
                .slug(slug)
                .description(req.getDescription())
                .iconUrl(req.getIconUrl())
                .parent(parent)
                .build();

        


        JobCategory savedCategory = repository.save(category);
        return JobCategoryMapper.toResponse(savedCategory, true);
    }

    @Override
    @Transactional(readOnly = true)
    public List<JobCategoryResponse> getAllCategories() {
        return repository.findAll()
                .stream()
                .map(j-> JobCategoryMapper.toResponse(j, false))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public JobCategoryResponse getCategoryById(Long id) {
        JobCategory category = getCategoryEntityById(id);
        return JobCategoryMapper.toResponse(category, true);
    }

    @Override
    @Transactional
    public JobCategoryResponse updateCategory(Long id, JobCategoryRequest req) {
        JobCategory category = getCategoryEntityById(id);


        if (!category.getName().equalsIgnoreCase(req.getName()) && repository.existsByName(req.getName())) {
            throw new BadRequestException("Category with name '" + req.getName() + "' already exists.");
        }

        category.setName(req.getName());
        category.setDescription(req.getDescription());
        category.setIconUrl(req.getIconUrl());
        category.setSlug(generateSlug(req.getName()));


        if (req.getParentId() != null) {

            if (req.getParentId().equals(id)) {
                throw new BadRequestException("A category cannot be its own parent.");
            }
            JobCategory parent = repository.findById(req.getParentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Parent category not found with id: " + req.getParentId()));
            category.setParent(parent);
        } else {
            category.setParent(null);
        }

        JobCategory updatedCategory = repository.save(category);
        return  JobCategoryMapper.toResponse(updatedCategory, true);
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        JobCategory category = getCategoryEntityById(id);

        repository.delete(category);
    }

    @Override
    @Transactional(readOnly = true)
    public JobCategory getCategoryEntityById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job category not found with id: " + id));
    }

    private String generateSlug(String input) {
        if (input == null) return "";
        return input.toLowerCase()
                .replaceAll("[^a-z0-9\\s]", "")
                .replaceAll("\\s+", "-")
                .trim();
    }
}