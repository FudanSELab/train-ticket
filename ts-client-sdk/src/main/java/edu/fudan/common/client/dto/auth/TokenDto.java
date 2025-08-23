package edu.fudan.common.client.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for authentication token response
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenDto {
    private String userId;
    private String username;
    private String token;
}
