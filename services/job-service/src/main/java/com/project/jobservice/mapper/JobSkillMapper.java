package com.project.jobservice.mapper;

import com.project.common.dto.response.JobSkillResponse;
import com.project.jobservice.modal.JobSkill;
import com.project.jobservice.payload.JobSkillRequest;
import org.springframework.stereotype.Component;

@Component
public class JobSkillMapper {
    private JobSkillMapper() {
    }


    public static JobSkillResponse toResponse(JobSkill entity) {
        if (entity == null) {
            return null;
        }

        return JobSkillResponse.builder()
                .id(entity.getId())
                .category(entity.getCategory())
                .name(entity.getName())
                .active(entity.getActive())
                .slug(entity.getSlug())
                .build();
    }

    public static JobSkill toEntity(JobSkillRequest request, String generatedSlug) {
        if (request == null) {
            return null;
        }

        return JobSkill.builder()
                .name(request.getName())
                .category(request.getCategory())
                .slug(generatedSlug)
                .active(true)
                .build();
    }
}