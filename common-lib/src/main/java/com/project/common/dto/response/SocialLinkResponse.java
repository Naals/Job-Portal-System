package com.project.common.dto.response;

import com.project.common.domain.SocialPlatform;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SocialLinkResponse {

    private SocialPlatform socialPlatform;
    private String url;

}
