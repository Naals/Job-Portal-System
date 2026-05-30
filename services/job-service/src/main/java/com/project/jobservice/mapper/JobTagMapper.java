package com.project.jobservice.mapper;

import com.project.common.dto.response.JobTagResponse;
import com.project.jobservice.modal.JobTag;
import com.project.jobservice.payload.JobTagRequest;
import org.springframework.stereotype.Component;

@Component
public class JobTagMapper {
    private JobTagMapper() {
    }


    public static JobTagResponse toResponse(JobTag jobTag) {
        if (jobTag == null) {
            return null;
        }

        return JobTagResponse.builder()
                .id(jobTag.getId())
                .name(jobTag.getName())
                .slug(jobTag.getSlug())
                .build();
    }

    public static JobTag toEntity(JobTagRequest request, String generatedSlug) {
        if (request == null) {
            return null;
        }

        return JobTag.builder()
                .name(request.getName())
                .slug(generatedSlug)
                .build();
    }
}