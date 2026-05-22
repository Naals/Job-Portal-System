package com.project.jobservice.modal.embeddable;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.math.BigDecimal;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalaryRange {

    private BigDecimal minSalary;
    private BigDecimal maxSalary;

}
