package com.project.companyservice.controller;

import com.project.common.domain.CompanyStatus;
import com.project.common.domain.CompanyType;
import com.project.common.domain.IndustryType;
import com.project.common.dto.ApiResponse;
import com.project.common.dto.CompanyRequest;
import com.project.common.dto.CompanyResponse;
import com.project.companyservice.service.CompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping
    public ResponseEntity<CompanyResponse> createCompany(
            @RequestHeader("X-User-Id") Long ownerId,
            @Valid @RequestBody CompanyRequest request
    ) {
        return ResponseEntity.ok(companyService.createCompany(ownerId, request));
    }

    @GetMapping("/{companyId}")
    public ResponseEntity<CompanyResponse> getCompanyById(
            @PathVariable Long companyId
    ) {
        return ResponseEntity.ok(companyService.getCompanyById(companyId));
    }

    @GetMapping("/me")
    public ResponseEntity<CompanyResponse> getMyCompany(
            @RequestHeader("X-User-Id") Long ownerId
    ) {
        return ResponseEntity.ok(companyService.getMyCompany(ownerId));
    }

    @GetMapping
    public ResponseEntity<List<CompanyResponse>> getAllCompanies(
            @RequestParam(required = false) CompanyType companyType,
            @RequestParam(required = false) IndustryType industryType,
            @RequestParam(required = false) CompanyStatus companyStatus
    ) {
        return ResponseEntity.ok(companyService.getAllCompanies(
                companyType,
                industryType,
                companyStatus
        ));
    }

    @PutMapping("/{companyId}")
    public ResponseEntity<CompanyResponse> updateCompany(
            @PathVariable Long companyId,
            @RequestHeader("X-User-Id") Long ownerId,
            @Valid @RequestBody CompanyRequest request
    ) {
        return ResponseEntity.ok(companyService.updateCompany(
                companyId,
                ownerId,
                request
        ));
    }

    @PatchMapping("/{companyId}/verify")
    public ResponseEntity<CompanyResponse> verifyCompany(
            @PathVariable Long companyId
    ) {
        return ResponseEntity.ok(companyService.verifyCompany(companyId));
    }

    @PatchMapping("/{companyId}/deactivate")
    public ResponseEntity<CompanyResponse> deactivateCompany(
            @PathVariable Long companyId
    ) {
        return ResponseEntity.ok(companyService.deactivateCompany(companyId));
    }

    @DeleteMapping("/{companyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<ApiResponse> deleteCompany(
            @PathVariable Long companyId,
            @RequestHeader("X-User-Id") Long ownerId
    ) {
        companyService.deleteCompany(companyId, ownerId);
        return ResponseEntity.ok(new ApiResponse("Company deleted successfully", true));
    }
}
