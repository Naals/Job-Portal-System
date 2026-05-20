package com.project.companyservice.modal;

import com.project.common.domain.CompanySize;
import lombok.*;
import jakarta.persistence.*;

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

    private Integer foundedYear;

    @Enumerated(EnumType.STRING)
    private CompanySize companySize;

}