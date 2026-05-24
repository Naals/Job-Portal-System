package com.project.jobservice.repository;

import com.project.common.domain.JobStatus;
import com.project.jobservice.modal.Job;
import com.project.jobservice.payload.JobSearchRequest;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import java.util.List;

public class JobSpecification {

    private JobSpecification() {
    }

    public static Specification<Job> build(JobSearchRequest req) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.isTrue(root.get("active")));

            JobStatus status = JobStatus.OPEN;
            predicates.add(cb.equal(root.get("status"), status));

            if (req.getJobType() != null) {
                predicates.add(cb.equal(root.get("jobType"), req.getJobType()));
            }
            if (req.getWorkMode() != null) {
                predicates.add(cb.equal(root.get("workMode"), req.getWorkMode()));
            }
            if (req.getExperienceLevel() != null) {
                predicates.add(cb.equal(root.get("experienceLevel"), req.getExperienceLevel()));
            }

            if (req.getCompanyId() != null) {
                predicates.add(cb.equal(root.get("companyId"), req.getCompanyId()));
            }
            if (req.getCategoryId() != null) {
                predicates.add(cb.equal(root.get("category").get("id"), req.getCategoryId()));
            }

            if (req.getKeyword() != null && !req.getKeyword().isBlank()) {
                String pattern = "%" + req.getKeyword().toLowerCase() + "%";
                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("title")), pattern),
                        cb.like(cb.lower(root.get("description")), pattern)
                ));
            }

            if (req.getLocation() != null && !req.getLocation().isBlank()) {
                String pattern = "%" + req.getLocation().toLowerCase() + "%";
                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("location").get("city")), pattern),
                        cb.like(cb.lower(root.get("location").get("state")), pattern),
                        cb.like(cb.lower(root.get("location").get("country")), pattern)
                ));
            }

            if (req.getMinSalary() != null) {
                predicates.add(cb.greaterThanOrEqualTo(
                        root.get("salaryRange").get("maxSalary"),
                        req.getMinSalary()
                ));
            }
            if (req.getMaxSalary() != null) {
                predicates.add(cb.lessThanOrEqualTo(
                        root.get("salaryRange").get("minSalary"),
                        req.getMaxSalary()
                ));
            }

            if (req.getSkillIds() != null && !req.getSkillIds().isEmpty()) {
                Join<Object, Object> skillsJoin = root.join("skills");
                predicates.add(skillsJoin.get("id").in(req.getSkillIds()));
            }
            if (req.getTagIds() != null && !req.getTagIds().isEmpty()) {
                Join<Object, Object> tagsJoin = root.join("tags");
                predicates.add(tagsJoin.get("id").in(req.getTagIds()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
