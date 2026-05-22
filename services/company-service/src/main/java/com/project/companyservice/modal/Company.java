package com.project.companyservice.modal;

import com.project.common.domain.CompanySize;
import com.project.common.domain.CompanyStatus;
import com.project.common.domain.CompanyType;
import com.project.common.domain.IndustryType;
import lombok.*;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "companies")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = true)
    private String slug;

    private String tagline;

    private String description;

    private String logoUrl;

    private String coverImageUrl;

    private String website;

    private String email;

    private String phone;

    private Integer foundedYear;

    @Enumerated(EnumType.STRING)
    private CompanySize companySize;

    @Enumerated(EnumType.STRING)
    private CompanyType companyType;

    @Enumerated(EnumType.STRING)
    private IndustryType industryType;

    @Enumerated(EnumType.STRING)
    private CompanyStatus companyStatus;


    private Boolean verified=false;

    @Column(unique = true)
    private String registrationNumber;

    @Column(nullable = false, unique = true)
    private Long ownerId;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<SocialLink> socialLinks = new ArrayList<>();

    private Boolean active = true;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}