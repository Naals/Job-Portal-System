package com.project.common.dto.response;

import com.project.common.domain.ExperienceLevel;
import com.project.common.domain.JobStatus;
import com.project.common.domain.JobType;
import com.project.common.domain.WorkMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobResponse {

    private Long id;
    private String title;
    private String description;
    private String requirements;
    private String responsibilities;
    private String benefits;

    private CompanyResponse company;
    private Long employerId;
    private JobCategoryResponse jobCategory;
    private Set<JobSkillResponse> jobSkill;
    private Set<JobTagResponse> jobTag;

    // Location
    private String address;
    private String city;
    private String state;
    private String country;
    private String zipCode;

    // Salary
    private BigDecimal minSalary;
    private BigDecimal maxSalary;

    // Classification
    private JobType jobType;
    private WorkMode workMode;
    private ExperienceLevel experienceLevel;
    private JobStatus status;

    // Posting details
    private Integer openings;
    private LocalDate applicationDeadline;
    private LocalDate expiresAt;
    private Boolean active;

    // Analytics
    private Long viewCount;
    private Long applicationCount;

    // Timestamps
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime publishedAt;
    private LocalDateTime closedAt;
}
