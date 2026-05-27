package com.project.jobservice.repository;

import com.project.jobservice.modal.JobCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobCategoryRepository extends JpaRepository<JobCategory, Long> {

    boolean existsByName(String name);
    boolean existsBySlug(String slug);
}
