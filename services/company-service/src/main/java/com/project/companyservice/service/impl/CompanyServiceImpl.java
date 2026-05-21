package com.project.companyservice.service.impl;

import com.project.common.domain.CompanyStatus;
import com.project.common.domain.CompanyType;
import com.project.common.domain.IndustryType;
import com.project.common.dto.CompanyRequest;
import com.project.common.dto.CompanyResponse;
import com.project.companyservice.modal.Company;
import com.project.companyservice.repository.CompanyRepository;
import com.project.companyservice.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    @Override
    public CompanyResponse createCompany(Long ownerId, CompanyRequest req) {
        return null;
    }

    private String generateUniqueSlug(String name){
        String base = name.toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")
                .trim().replaceAll("[\\s-]", "");

        if(companyRepository.existsBySlug(base)){
            return base;
        }
        int counter = 1;
        while(companyRepository.existsBySlug(base+ "-" +counter)){
            counter++;
        }

        return base+"-"+counter;
    }

    @Override
    public CompanyResponse getCompanyById(Long id) {
        return null;
    }

    @Override
    public CompanyResponse getMyCompany(Long ownerId) {
        return null;
    }

    @Override
    public List<CompanyResponse> getAllCompanies(CompanyType companyType, IndustryType industryType, CompanyStatus companyStatus) {
        return List.of();
    }

    @Override
    public CompanyResponse updateCompany(Long companyId, Long ownerId, CompanyRequest req) {
        return null;
    }

    @Override
    public CompanyResponse verifyCompany(Long companyId) {
        return null;
    }

    @Override
    public void deleteCompany(Long companyId) {

    }

    @Override
    public CompanyResponse deactivateCompany(Long companyId) {
        return null;
    }

    @Override
    public Company getCompanyEntityById(Long id) {
        return null;
    }
}
