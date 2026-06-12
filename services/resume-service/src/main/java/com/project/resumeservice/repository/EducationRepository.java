package com.project.resumeservice.repository;


import com.project.resumeservice.modal.Education;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EducationRepository extends JpaRepository<Education, Long> {

    List<Education> findByResumeIdOrderByDisplayOrderAsc(Long resumeId);
}
