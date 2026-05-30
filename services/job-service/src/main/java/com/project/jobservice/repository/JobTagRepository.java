package com.project.jobservice.repository;

import com.project.jobservice.modal.JobTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobTagRepository extends JpaRepository<JobTag, Long> {

    boolean existsByName(String name);
    boolean existsBySlug(String slug);
}
