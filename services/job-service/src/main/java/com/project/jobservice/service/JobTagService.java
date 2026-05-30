package com.project.jobservice.service;

import com.project.common.dto.response.JobTagResponse;
import com.project.jobservice.modal.JobTag;
import com.project.jobservice.payload.JobTagRequest;

import java.util.List;
import java.util.Set;

public interface JobTagService {

    JobTagResponse createTag(JobTagRequest req);

    List<JobTagResponse> getAllTags();

    JobTagResponse getById(Long id);

    JobTagResponse updateTag(Long id, JobTagRequest req);

    void deleteTag(Long id);

    JobTag getTagEntityById(Long id);

    Set<JobTag> getTagsByIds(Set<Long> ids);
}
