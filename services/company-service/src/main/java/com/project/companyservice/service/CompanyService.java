package com.project.companyservice.service;

import com.project.common.domain.*;
import com.project.common.dto.CompanyRequest;
import com.project.common.dto.CompanyResponse;
import com.project.companyservice.modal.Company;

import java.util.List;

public interface CompanyService {

    CompanyResponse createCompany(Long ownerId, CompanyRequest req);

    CompanyResponse getCompanyById(Long id);

    CompanyResponse getMyCompany(Long ownerId);

    List<CompanyResponse> getAllCompanies(
            CompanyType companyType,
            IndustryType industryType,
            CompanyStatus companyStatus
    );

    CompanyResponse updateCompany(Long companyId, Long ownerId, CompanyRequest req);

    CompanyResponse verifyCompany(Long companyId);

    void deleteCompany(Long companyId,  Long ownerId);

    CompanyResponse deactivateCompany(Long companyId);

    Company getCompanyEntityById(Long id);
}
