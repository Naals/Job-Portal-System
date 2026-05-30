package com.project.jobservice.service.impl;

import com.project.common.domain.JobStatus;
import com.project.common.dto.request.JobRequest;
import com.project.common.dto.response.CompanyResponse;
import com.project.common.dto.response.JobResponse;
import com.project.jobservice.exception.*;
import com.project.jobservice.mapper.JobMapper;
import com.project.jobservice.modal.Job;
import com.project.jobservice.modal.JobCategory;
import com.project.jobservice.modal.JobSkill;
import com.project.jobservice.modal.JobTag;
import com.project.jobservice.payload.JobSearchRequest;
import com.project.jobservice.repository.JobRepository;
import com.project.jobservice.repository.JobSpecification;
import com.project.jobservice.service.JobCategoryService;
import com.project.jobservice.service.JobService;

import java.util.Collections;
import java.util.List;

import com.project.jobservice.service.JobSkillService;
import com.project.jobservice.service.JobTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final JobCategoryService jobCategoryService;
    private final JobSkillService jobSkillService;
    private final JobTagService jobTagService;

    @Override
    @Transactional
    public JobResponse createJob(Long employerId, JobRequest req) {

        JobCategory category = jobCategoryService.getCategoryEntityById(req.getCategoryId());

        Set<JobSkill> jobSkillSet = req.getSkillIds()!=null
                 ?jobSkillService.getSkillsByIds(req.getSkillIds())
                : Collections.emptySet();

        Set<JobTag> jobTagSet = req.getTagIds() != null
                ? jobTagService.getTagsByIds(req.getTagIds())
                : Collections.emptySet();

        Job job = JobMapper.toEntity(req, employerId, category, jobSkillSet, jobTagSet);

        Job savedJob = jobRepository.save(job);
        return toConvertResponse(savedJob);
    }


    @Override
    public JobResponse getJobById(Long id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + id));

        return toConvertResponse(job);
    }

    @Override
    public List<JobResponse> getJobs(JobSearchRequest request) {
        Specification<Job> spec = JobSpecification.build(request);

        return jobRepository.findAll(spec).stream()
                .map(job -> JobMapper.toResponse(job, CompanyResponse.builder().id(job.getCompanyId()).build()))
                .toList();
    }

    @Override
    public List<JobResponse> getJobsByCompany(Long companyId) {
        return jobRepository.findByCompanyId(companyId).stream()
                .map(this::toConvertResponse)
                .toList();
    }

    @Override
    @Transactional
    public JobResponse updateJob(Long jobId, Long employerId, JobRequest req) {
        Job job = verifyAndGetJob(jobId, employerId);

        JobCategory category = jobCategoryService.getCategoryEntityById(req.getCategoryId());

        Set<JobSkill> jobSkillSet = req.getSkillIds()!=null
                ?jobSkillService.getSkillsByIds(req.getSkillIds())
                : Collections.emptySet();

        Set<JobTag> jobTagSet = req.getTagIds() != null
                ? jobTagService.getTagsByIds(req.getTagIds())
                : Collections.emptySet();

        JobMapper.updateEntityFromDto(req, job, category, jobSkillSet, jobTagSet);
        job.setUpdatedAt(LocalDateTime.now());

        Job updatedJob = jobRepository.save(job);
        return toConvertResponse(updatedJob);
    }

    @Override
    @Transactional
    public JobResponse publishJob(Long jobId, Long employerId) {
        Job job = verifyAndGetJob(jobId, employerId);

        job.setJobStatus(JobStatus.OPEN);
        job.setActive(true);
        job.setPublishedAt(LocalDateTime.now());
        job.setUpdatedAt(LocalDateTime.now());

        return toConvertResponse(job);
    }

    @Override
    @Transactional
    public JobResponse closeJob(Long jobId, Long employerId) {
        Job job = verifyAndGetJob(jobId, employerId);

        job.setJobStatus(JobStatus.CLOSED);
        job.setActive(false);
        job.setClosedAt(LocalDateTime.now());
        job.setUpdatedAt(LocalDateTime.now());

        return toConvertResponse(job);
    }

    @Override
    @Transactional
    public JobResponse deleteJob(Long jobId, Long employerId) {
        Job job = verifyAndGetJob(jobId, employerId);

        jobRepository.delete(job);

        return toConvertResponse(job);
    }

    @Override
    public List<JobResponse> getAllJobsAdmin() {
        return jobRepository.findAll().stream()
                .map(this::toConvertResponse)
                .toList();
    }

    private Job verifyAndGetJob(Long jobId, Long employerId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + jobId));

        if (!job.getEmployerId().equals(employerId)) {
            throw new UnauthorizedAccessException("You are not authorized to modify this job post.");
        }
        return job;
    }

    private JobResponse toConvertResponse(Job job) {
        return JobMapper.toResponse(job, CompanyResponse.builder().id(job.getCompanyId()).build());
    }
}