package com.project.jobservice.service;


import com.project.common.dto.response.JobSkillResponse;
import com.project.jobservice.modal.JobSkill;
import com.project.jobservice.payload.JobSkillRequest;

import java.util.*;

public interface JobSkillService {

    JobSkillResponse createSkill(JobSkillRequest req);

    List<JobSkillResponse> getAllSkills();

    JobSkillResponse getSkillById(Long id);

    JobSkillResponse updateSkill(Long id, JobSkillRequest req);

    void deleteSkill(Long id);

    Set<JobSkill> getSkillsByIds(Set<Long> ids);
}