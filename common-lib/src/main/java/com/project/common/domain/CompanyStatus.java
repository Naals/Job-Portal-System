package com.project.common.domain;


public enum CompanyStatus {
    PENDING_VERIFICATION, // Just registered, awaiting approval
    ACTIVE,               // Verified and active on the platform
    SUSPENDED,            // Temporarily hidden or blocked
    REJECTED              // Application denied verification
}
