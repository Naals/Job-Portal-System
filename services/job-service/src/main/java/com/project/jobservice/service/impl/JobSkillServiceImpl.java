package com.project.jobservice.service.impl;

import com.project.common.dto.response.JobSkillResponse;
import com.project.jobservice.exception.ResourceAlreadyExistsException;
import com.project.jobservice.mapper.JobSkillMapper;
import com.project.jobservice.modal.JobSkill;
import com.project.jobservice.payload.JobSkillRequest;
import com.project.jobservice.repository.JobSkillRepository;
import com.project.jobservice.service.JobSkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobSkillServiceImpl implements JobSkillService {

    private final JobSkillRepository jobSkillRepository;

    @Override
    public JobSkillResponse createSkill(JobSkillRequest req) {
        if (jobSkillRepository.existsByName(req.getName())) {
            throw new ResourceAlreadyExistsException("Job skill with name '" + req.getName() + "' already exists");
        }

        String slug = generateSlug(req.getName());

        JobSkill jobSkill = JobSkill.builder()
                .name(req.getName())
                .slug(slug)
                .category(req.getCategory())
                .active(true)
                .build();

        JobSkill savedSkill = jobSkillRepository.save(jobSkill);
        return JobSkillMapper.toResponse(savedSkill);
    }

    @Override
    public List<JobSkillResponse> getAllSkills() {
        return jobSkillRepository.findByActiveTrue().stream()
                .map(JobSkillMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public JobSkillResponse getSkillById(Long id) {
        JobSkill jobSkill = findSkillByIdOrThrow(id);
        return JobSkillMapper.toResponse(jobSkill);
    }

    @Override
    public JobSkillResponse updateSkill(Long id, JobSkillRequest req) {
        JobSkill existingSkill = findSkillByIdOrThrow(id);

        if (!existingSkill.getName().equals(req.getName()) && jobSkillRepository.existsByName(req.getName())) {
            throw new ResourceAlreadyExistsException("Job skill with name '" + req.getName() + "' already exists");
        }

        existingSkill.setName(req.getName());
        existingSkill.setCategory(req.getCategory());
        existingSkill.setSlug(generateSlug(req.getName()));

        JobSkill updatedSkill = jobSkillRepository.save(existingSkill);
        return JobSkillMapper.toResponse(updatedSkill);
    }

    @Override
    public void deleteSkill(Long id) {
        JobSkill existingSkill = findSkillByIdOrThrow(id);

        existingSkill.setActive(false);
    }

    @Override
    public Set<JobSkill> getSkillsByIds(Set<Long> ids) {
        return new HashSet<>(jobSkillRepository.findAllById(ids));
    }

    private JobSkill findSkillByIdOrThrow(Long id) {
        return jobSkillRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job skill not found with id: " + id));
    }

    private String generateSlug(String name) {
        if (name == null) return "";
        return name.toLowerCase()
                .trim()
                .replaceAll("[^a-z0-9\\s-]", "")
                .replaceAll("\\s+", "-");
    }
}