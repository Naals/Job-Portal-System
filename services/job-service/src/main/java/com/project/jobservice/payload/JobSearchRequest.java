package com.project.jobservice.payload;

import com.project.common.domain.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobSearchRequest {

    private String keyword;

    private Long categoryId;

    private List<Long> skillIds;

    private List<Long> tagIds;

    private Long companyId;

    /** Matches city, state, or country (case-insensitive LIKE). */
    private String location;

    /** Salary overlap - job's max salary must be >= minSalary. */
    private BigDecimal minSalary;

    /** Salary overlap - job's min salary must be <= maxSalary. */
    private BigDecimal maxSalary;

    private JobType jobType;

    private WorkMode workMode;

    private ExperienceLevel experienceLevel;

    private JobStatus jobStatus;

    private Integer minOpenings;
    private Integer maxOpenings;
}
