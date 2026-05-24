package com.project.jobservice.modal.embeddable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable 
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobLocation {

    private String address;
    private String city;
    private String country;
    private String state;
    private String zipCode;

}