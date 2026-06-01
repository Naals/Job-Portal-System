package com.project.resumeservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ResumeAccessDeniedException extends RuntimeException {
    public ResumeAccessDeniedException(Long candidateId, Long resumeId) {
        super(String.format("Candidate with ID %d is not authorized to access resume with ID %d", candidateId, resumeId));
    }
}
