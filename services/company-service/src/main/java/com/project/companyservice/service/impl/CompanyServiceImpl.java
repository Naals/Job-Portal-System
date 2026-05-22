package com.project.companyservice.service.impl;

import com.project.common.domain.CompanyStatus;
import com.project.common.domain.CompanyType;
import com.project.common.domain.IndustryType;
import com.project.common.dto.CompanyRequest;
import com.project.common.dto.CompanyResponse;
import com.project.common.dto.SocialLinkResponse;
import com.project.companyservice.exception.*;
import com.project.companyservice.mapper.CompanyMapper;
import com.project.companyservice.modal.Company;
import com.project.companyservice.modal.SocialLink;
import com.project.companyservice.repository.CompanyRepository;
import com.project.companyservice.service.CompanyService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;



    @Override
    @Transactional
    public CompanyResponse createCompany(Long ownerId, CompanyRequest req) {

        if (companyRepository.existsByOwnerId(ownerId)) {
            throw new AccountLimitExceededException("You already have a company registered. Only one company per account is allowed.");
        }

        if (companyRepository.existsByName(req.getName())) {
            throw new CompanyAlreadyExistsException("Company already exists. Please choose a different name.");
        }

        if (req.getRegistrationNumber() != null &&
                companyRepository.existsByRegistrationNumber(req.getRegistrationNumber())) {
            throw new CompanyAlreadyExistsException("Company already exists. Please choose a different registration number.");
        }

        String slug = generateUniqueSlug(req.getName());

        Company company = Company.builder()
                .name(req.getName())
                .slug(slug)
                .tagline(req.getTagline())
                .description(req.getDescription())
                .logoUrl(req.getLogoUrl())
                .coverImageUrl(req.getCoverImageUrl())
                .website(req.getWebsite())
                .email(req.getEmail())
                .phone(req.getPhone())
                .foundedYear(req.getFoundedYear())
                .companySize(req.getCompanySize())
                .companyType(req.getCompanyType())
                .industryType(req.getIndustryType())
                .registrationNumber(req.getRegistrationNumber())
                .ownerId(ownerId)
                .socialLinks(mapSocialLinks(req.getSocialLinks()))
                .companyStatus(CompanyStatus.PENDING_VERIFICATION)
                .build();

        Company savedCompany = companyRepository.save(company);
        return CompanyMapper.toDTO(savedCompany);
    }

    private List<SocialLink> mapSocialLinks(List<SocialLinkResponse> socialLinks) {
        if (socialLinks == null || socialLinks.isEmpty()) {
            return new ArrayList<>();
        }

        return socialLinks.stream()
                .map(s -> SocialLink.builder()
                        .socialPlatform(s.getSocialPlatform())
                        .url(s.getUrl())
                        .build()
        ).toList();
    }

    private String generateUniqueSlug(String name) {

        String base = name.toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")
                .trim()
                .replaceAll("[\\s]+", "-");

        if (!companyRepository.existsBySlug(base)) {
            return base;
        }

        int counter = 1;
        while (companyRepository.existsBySlug(base + "-" + counter)) {
            counter++;
        }

        return base + "-" + counter;
    }

    @Override
    public CompanyResponse getCompanyById(Long id) {
        Company company = getCompanyEntityById(id);
        return CompanyMapper.toDTO(company);
    }

    @Override
    public CompanyResponse getMyCompany(Long ownerId) {
        Company company = companyRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("No company profile found for owner ID: " + ownerId));
        return CompanyMapper.toDTO(company);
    }

    @Override
    public List<CompanyResponse> getAllCompanies(CompanyType companyType, IndustryType industryType, CompanyStatus companyStatus) {
        List<Company> companies = companyRepository.findByFilters(companyType, industryType, companyStatus);

        return companies.stream()
                .map(CompanyMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional
    public CompanyResponse updateCompany(Long companyId, Long ownerId, CompanyRequest req) {
        Company company = getCompanyEntityById(companyId);

        assertOwner(company, ownerId);

        if (req.getName() != null && !req.getName().equals(company.getName())) {
            if (companyRepository.existsByName(req.getName())) {
                throw new CompanyAlreadyExistsException(String.format("Company already exists: %s. Please choose a different name.",  req.getName()));
            }
            company.setName(req.getName());
            company.setSlug(generateUniqueSlug(req.getName()));
        }

        if (req.getRegistrationNumber() != null && !req.getRegistrationNumber().equals(company.getRegistrationNumber())) {
            if (companyRepository.existsByRegistrationNumber(req.getRegistrationNumber())) {
                throw new CompanyAlreadyExistsException("Company already exists with this registration number: " + company.getRegistrationNumber());
            }
            company.setRegistrationNumber(req.getRegistrationNumber());
        }

        if (req.getTagline() != null) company.setTagline(req.getTagline());
        if (req.getDescription() != null) company.setDescription(req.getDescription());
        if (req.getLogoUrl() != null) company.setLogoUrl(req.getLogoUrl());
        if (req.getCoverImageUrl() != null) company.setCoverImageUrl(req.getCoverImageUrl());
        if (req.getWebsite() != null) company.setWebsite(req.getWebsite());
        if (req.getEmail() != null) company.setEmail(req.getEmail());
        if (req.getPhone() != null) company.setPhone(req.getPhone());
        if (req.getFoundedYear() != null) company.setFoundedYear(req.getFoundedYear());
        if (req.getCompanySize() != null) company.setCompanySize(req.getCompanySize());
        if (req.getCompanyType() != null) company.setCompanyType(req.getCompanyType());
        if (req.getIndustryType() != null) company.setIndustryType(req.getIndustryType());

        if (req.getSocialLinks() != null) {
            company.getSocialLinks().clear();
            company.getSocialLinks().addAll(mapSocialLinks(req.getSocialLinks()));
        }

        return CompanyMapper.toDTO(company);
    }

    @Override
    @Transactional
    public CompanyResponse verifyCompany(Long companyId) {
        Company company = getCompanyEntityById(companyId);
        company.setCompanyStatus(CompanyStatus.ACTIVE);
        company.setVerified(true);
        return CompanyMapper.toDTO(company);
    }

    @Override
    @Transactional
    public CompanyResponse deactivateCompany(Long companyId) {
        Company company = getCompanyEntityById(companyId);
        company.setCompanyStatus(CompanyStatus.SUSPENDED);

        return CompanyMapper.toDTO(company);
    }

    @Override
    @Transactional
    public void deleteCompany(Long companyId, Long ownerId) {
        Company company = getCompanyEntityById(companyId);
        assertOwner(company, ownerId);
        companyRepository.delete(company);
    }

    private void assertOwner(Company company, Long ownerId) {
        if (!company.getOwnerId().equals(ownerId)) {
            throw new UnauthorizedAccessException("Unauthorized: You do not own this company profile.");
        }
    }

    @Override
    @Transactional
    public Company getCompanyEntityById(Long id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company record not located under ID: " + id));
    }
}
