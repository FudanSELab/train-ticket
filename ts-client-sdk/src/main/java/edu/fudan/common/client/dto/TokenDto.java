package edu.fudan.common.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for authentication token response
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenDto {
    private String userId;
    private String username;
    private String token;
}
