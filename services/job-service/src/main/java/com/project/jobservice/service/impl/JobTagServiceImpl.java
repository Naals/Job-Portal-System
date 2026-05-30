package com.project.jobservice.service.impl;

import com.project.common.dto.response.JobTagResponse;
import com.project.jobservice.exception.ResourceAlreadyExistsException;
import com.project.jobservice.exception.ResourceNotFoundException;
import com.project.jobservice.mapper.JobTagMapper;
import com.project.jobservice.modal.JobTag;
import com.project.jobservice.payload.JobTagRequest;
import com.project.jobservice.repository.JobTagRepository;
import com.project.jobservice.service.JobTagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class JobTagServiceImpl implements JobTagService {

    private final JobTagRepository jobTagRepository;

    @Override
    public JobTagResponse createTag(JobTagRequest req) {
        log.info("Creating new job tag with name: {}", req.getName());

        if (jobTagRepository.existsByNameIgnoreCase(req.getName())) {
            throw new ResourceAlreadyExistsException("Job tag already exists with name: " + req.getName());
        }

        JobTag jobTag = JobTag.builder()
                .name(req.getName())
                .slug(generateSlug(req.getName()))
                .build();

        JobTag savedTag = jobTagRepository.save(jobTag);

        return JobTagMapper.toResponse(savedTag);
    }

    @Override
    public List<JobTagResponse> getAllTags() {
        log.info("Fetching all job tags");

        return jobTagRepository.findAll()
                .stream()
                .map(JobTagMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public JobTagResponse getById(Long id) {
        log.info("Fetching job tag with id: {}", id);

        JobTag jobTag = getTagEntityById(id);
        return JobTagMapper.toResponse(jobTag);
    }

    @Override
    public JobTagResponse updateTag(Long id, JobTagRequest req) {
        log.info("Updating job tag with id: {}", id);

        JobTag existingTag = getTagEntityById(id);

        existingTag.setName(req.getName());
        existingTag.setSlug(generateSlug(req.getName()));

        JobTag updatedTag = jobTagRepository.save(existingTag);

        return JobTagMapper.toResponse(updatedTag);
    }

    @Override
    public void deleteTag(Long id) {
        log.info("Deleting job tag with id: {}", id);

        JobTag existingTag = getTagEntityById(id);
        jobTagRepository.delete(existingTag);
    }

    @Override
    public JobTag getTagEntityById(Long id) {
        return jobTagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("JobTag not found with id: " + id));
    }

    @Override
    public Set<JobTag> getTagsByIds(Set<Long> ids) {
        return jobTagRepository.findAllById(ids);
    }


    private String generateSlug(String input) {
        if (input == null || input.isEmpty()) {
            return "";
        }
        return input.toLowerCase()
                .trim()
                .replaceAll("[^a-z0-9\\s-]", "")
                .replaceAll("\\s+", "-");
    }
}