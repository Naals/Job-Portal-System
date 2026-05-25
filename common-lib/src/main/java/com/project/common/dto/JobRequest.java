package com.project.common.dto;

import com.project.common.domain.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobRequest {

    @NotBlank(message = "job title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    private String requirements;
    private String responsibilities;
    private String benefits;

    @NotNull(message = "Category is required")
    private Long categoryId;

    /** IDs from the job_skills table. */
    private Set<Long> skillIds;

    /** IDs from the job_tags table. */
    private Set<Long> tagIds;

    private String address;
    private String city;
    private String state;
    private String country;
    private String zipCode;


    @DecimalMin(value = "0.0", inclusive = true, message = "Min salary must not be negative")
    private BigDecimal minSalary;

    @DecimalMin(value = "0.0", inclusive = true, message = "Max salary must not be negative")
    private BigDecimal maxSalary;

    /** ISO 4217 currency code, e.g. "USD", "INR". Defaults to "USD". */
    private String currency;

    @NotNull(message = "Job type is required")
    private JobType jobType;

    @NotNull(message = "Work mode is required")
    private WorkMode workMode;

    @NotNull(message = "Experience level is required")
    private ExperienceLevel experienceLevel;


    @Min(value = 1, message = "Openings must be at least 1")
    private Integer openings = 1;

    private LocalDate applicationDeadline;
    private LocalDate expiresAt;

}
