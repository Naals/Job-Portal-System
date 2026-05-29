package com.project.jobservice.repository;


import com.project.jobservice.modal.JobSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobSkillRepository extends JpaRepository<JobSkill, Long> {

    List<JobSkill> findByActiveTrue();

    boolean existsByName(String name);

    boolean existsBySlug(String slug);
}