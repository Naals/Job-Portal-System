package com.project.jobservice.service;

import com.project.common.dto.JobRequest;
import com.project.common.dto.JobResponse;
import com.project.jobservice.payload.JobSearchRequest;

import java.util.List;

public interface JobService {

    JobResponse createJob(Long employerId, JobRequest req);

    JobResponse getJobById(Long id);

    List<JobResponse> getJobs(JobSearchRequest request);

    List<JobResponse> getJobsByCompany(Long companyId);

    JobResponse updateJob(Long jobId, Long employerId, JobRequest req);

    JobResponse publishJob(Long jobId, Long employerId);

    JobResponse closeJob(Long jobId, Long employerId);

    JobResponse deleteJob(Long jobId, Long employerId);

    List<JobResponse> getAllJobsAdmin();
}
