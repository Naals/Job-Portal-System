package com.project.companyservice.modal;

import com.project.common.domain.SocialPlatform;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SocialLink {

    private SocialPlatform socialPlatform;
    private String url;
}
