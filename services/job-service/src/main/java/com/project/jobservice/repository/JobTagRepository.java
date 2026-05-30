package com.project.jobservice.repository;

import com.project.jobservice.modal.JobTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface JobTagRepository extends JpaRepository<JobTag, Long> {

    boolean existsByName(String name);
    boolean existsBySlug(String slug);
    boolean existsByNameIgnoreCase(String name);
    Set<JobTag> findAllById(Set<Long> slug);
}
