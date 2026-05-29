package com.project.jobservice.mapper;

import com.project.common.dto.response.JobCategoryResponse;
import com.project.jobservice.modal.JobCategory;
import com.project.jobservice.payload.JobCategoryRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class JobCategoryMapper {

    public static JobCategory toEntity(JobCategoryRequest request, JobCategory parent) {
        if (request == null) {
            return null;
        }

        return JobCategory.builder()
                .name(request.getName())
                .description(request.getDescription())
                .iconUrl(request.getIconUrl())
                .parent(parent)
                .build();
    }



    public static JobCategoryResponse toResponse(JobCategory category, boolean includeChildren) {

        List<JobCategoryResponse> subCategories = null;

        if (includeChildren) {
            subCategories = category.getSubCategories()
                    .stream().map(sub -> toResponse(sub, false))
                    .collect(Collectors.toList());
        }

        return JobCategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .slug(category.getSlug())
                .description(category.getDescription())
                .iconUrl(category.getIconUrl())
                .active(category.getActive())
                .parentId(category.getParent() != null ? category.getParent().getId() : null)
                .parentName(category.getParent() != null ? category.getParent().getName() : null)
                .subCategories(subCategories)
                .createdAt(category.getCreatedAt())
                .build();
    }

    public static void updateEntityFromRequest(JobCategoryRequest request, JobCategory entity) {
        if (request == null || entity == null) {
            return;
        }

        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setIconUrl(request.getIconUrl());
    }
}
