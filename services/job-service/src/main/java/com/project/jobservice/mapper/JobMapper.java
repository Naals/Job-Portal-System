package com.project.jobservice.mapper;

import com.project.common.dto.request.JobRequest;
import com.project.common.dto.response.CompanyResponse;
import com.project.common.dto.response.JobResponse;
import com.project.jobservice.modal.Job;
import com.project.jobservice.modal.JobCategory;
import com.project.jobservice.modal.JobSkill;
import com.project.jobservice.modal.JobTag;
import com.project.jobservice.modal.embeddable.JobLocation;
import com.project.jobservice.modal.embeddable.SalaryRange;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JobMapper {

    public static Job toEntity(JobRequest req, Long employerId, JobCategory category, Set<JobSkill> skills, Set<JobTag> tags) {
        if (req == null) return null;

        return Job.builder()
                .title(req.getTitle())
                .description(req.getDescription())
                .requirements(req.getRequirements())
                .responsibilities(req.getResponsibilities())
                .benefits(req.getBenefits())
                .employerId(employerId)
                .jobCategory(category)
                .jobTag(tags)
                .jobSkill(skills)
                .location(buildLocation(req))
                .salaryRange(buildSalaryRange(req))
                .jobType(req.getJobType())
                .workMode(req.getWorkMode())
                .experienceLevel(req.getExperienceLevel())
                .openings(req.getOpenings() != null ? req.getOpenings() : 1)
                .applicationDeadline(req.getApplicationDeadline())
                .expiresAt(req.getExpiresAt())
                .build();
    }

    public static JobResponse toResponse(Job job, CompanyResponse companyResponse) {
        JobLocation loc = job.getLocation();
        SalaryRange sal = job.getSalaryRange(); // Added to resolve 'sal' variables below

        return JobResponse.builder()
                .id(job.getId())
                .title(job.getTitle())
                .description(job.getDescription())
                .requirements(job.getRequirements())
                .responsibilities(job.getResponsibilities())
                .benefits(job.getBenefits())
                .company(companyResponse)
                .employerId(job.getEmployerId())
                .jobCategory(JobCategoryMapper.toResponse(job.getJobCategory(), false))
                .jobTag(job.getJobTag().stream().map(JobTagMapper::toResponse).collect(Collectors.toSet()))
                .jobSkill(job.getJobSkill().stream().map(JobSkillMapper::toResponse).collect(Collectors.toSet()))
                .address(loc != null ? loc.getAddress() : null)
                .city(loc != null ? loc.getCity() : null)
                .state(loc != null ? loc.getState() : null)
                .country(loc != null ? loc.getCountry() : null)
                .zipCode(loc != null ? loc.getZipCode() : null)
                .minSalary(sal != null ? sal.getMinSalary() : null)
                .maxSalary(sal != null ? sal.getMaxSalary() : null)
                .jobType(job.getJobType())
                .workMode(job.getWorkMode())
                .experienceLevel(job.getExperienceLevel())
                .status(job.getJobStatus())
                .openings(job.getOpenings())
                .applicationDeadline(job.getApplicationDeadline())
                .expiresAt(job.getExpiresAt())
                .active(job.getActive())
                .createdAt(job.getCreatedAt())
                .updatedAt(job.getUpdatedAt())
                .publishedAt(job.getPublishedAt())
                .closedAt(job.getClosedAt())
                .build();
    }

    public static void updateEntityFromDto(JobRequest req, Job job, JobCategory category, Set<JobSkill> skills, Set<JobTag> tags) {
        if (req == null || job == null) return;

        job.setTitle(req.getTitle());
        job.setDescription(req.getDescription());
        job.setJobCategory(category);
        job.setJobSkill(skills);
        job.setJobTag(tags);
        job.setRequirements(req.getRequirements());
        job.setResponsibilities(req.getResponsibilities());
        job.setBenefits(req.getBenefits());
        job.setJobType(req.getJobType());
        job.setWorkMode(req.getWorkMode());
        job.setExperienceLevel(req.getExperienceLevel());
        job.setOpenings(req.getOpenings() != null ? req.getOpenings() : 1);
        job.setApplicationDeadline(req.getApplicationDeadline());
        job.setExpiresAt(req.getExpiresAt());

        job.setLocation(buildLocation(req));
        job.setSalaryRange(buildSalaryRange(req));
    }

    public static SalaryRange buildSalaryRange(JobRequest req) {
        return SalaryRange.builder()
                .minSalary(req.getMinSalary())
                .maxSalary(req.getMaxSalary())
                .build();
    }

    public static JobLocation buildLocation(JobRequest req) {
        return JobLocation.builder()
                .address(req.getAddress())
                .city(req.getCity())
                .state(req.getState())
                .country(req.getCountry())
                .zipCode(req.getZipCode())
                .build();
    }
}
