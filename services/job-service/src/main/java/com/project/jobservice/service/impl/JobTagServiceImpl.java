package com.project.jobservice.service.impl;

import com.project.common.dto.response.JobTagResponse;
import com.project.jobservice.modal.JobTag;
import com.project.jobservice.payload.JobTagRequest;
import com.project.jobservice.repository.JobTagRepository;
import com.project.jobservice.service.JobTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobTagServiceImpl implements JobTagService {

    private final JobTagRepository jobTagRepository;

    @Override
    public JobTagResponse createTag(JobTagRequest req) {
        return null;
    }

    @Override
    public List<JobTagResponse> getAllTags() {
        return List.of();
    }

    @Override
    public JobTagResponse getById(Long id) {
        return null;
    }

    @Override
    public JobTagResponse updateTag(Long id, JobTagRequest req) {
        return null;
    }

    @Override
    public void deleteTag(Long id) {

    }

    @Override
    public JobTag getTagEntityById(Long id) {
        return null;
    }
}
