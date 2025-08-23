package edu.fudan.common.client.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for user login
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BasicAuthDto {
    private String username;
    private String password;
    private String verificationCode;
}
