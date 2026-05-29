package com.project.companyservice.mapper;



import com.project.common.domain.*;
import com.project.common.dto.response.CompanyResponse;
import com.project.common.dto.response.SocialLinkResponse;
import com.project.companyservice.modal.Company;
import com.project.companyservice.modal.SocialLink;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CompanyMapper {

    public static CompanyResponse toDTO(Company company) {
        if (company == null) {
            return null;
        }

        return CompanyResponse.builder()

                .id(company.getId())
                .name(company.getName())
                .slug(company.getSlug())
                .tagline(company.getTagline())
                .description(company.getDescription())
                .logoUrl(company.getLogoUrl())
                .coverImageUrl(company.getCoverImageUrl())
                .website(company.getWebsite())
                .email(company.getEmail())
                .phone(company.getPhone())
                .foundedYear(company.getFoundedYear())
                .companySize(company.getCompanySize())
                .companyType(company.getCompanyType())
                .industryType(company.getIndustryType())
                .verified(company.getVerified())
                .status(company.getCompanyStatus())
                .ownerId(company.getOwnerId())
                .socialLinks(mapSocialLinksToResponse(company.getSocialLinks()))
                .verified(company.getCompanyStatus() == CompanyStatus.ACTIVE)
                .active(company.getCompanyStatus() != CompanyStatus.SUSPENDED && company.getCompanyStatus() != CompanyStatus.REJECTED)
                .createdAt(company.getCreatedAt())
                .updatedAt(company.getUpdatedAt())

                .build();
    }

    private static List<SocialLinkResponse> mapSocialLinksToResponse(List<SocialLink> links) {
        if (links == null || links.isEmpty()) {
            return new ArrayList<>();
        }

        return links.stream()
                .map(link -> {
                    SocialLinkResponse response = new SocialLinkResponse();
                    response.setSocialPlatform(link.getSocialPlatform());
                    response.setUrl(link.getUrl());
                    return response;
                })
                .collect(Collectors.toList());
    }
}