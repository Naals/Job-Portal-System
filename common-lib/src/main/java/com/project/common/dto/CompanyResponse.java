package com.project.common.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.project.common.domain.CompanySize;
import com.project.common.domain.CompanyStatus;
import com.project.common.domain.CompanyType;
import com.project.common.domain.IndustryType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyResponse {

    private Long id;
    private String name;
    private String slug;
    private String tagline;
    private String description;
    private String logoUrl;
    private String coverImageUrl;
    private String website;
    private String email;
    private String phone;
    private Integer foundedYear;

    private CompanySize companySize;
    private CompanyType companyType;
    private IndustryType industryType;
    private CompanyStatus status;
    private Boolean verified;
    private Boolean active;

    private Long ownerId;

    private List<SocialLinkResponse> socialLinks;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime verifiedAt;

}
