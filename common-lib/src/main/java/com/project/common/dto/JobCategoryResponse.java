package com.project.common.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobCategoryResponse {

    private Long id;
    private String name;
    private String slug;
    private String description;
    private String iconUrl;
    private Boolean active;

    private Long parentId;
    private String parentName;

    private List<JobCategoryResponse> subCategories;

    private LocalDateTime createdAt;
}
