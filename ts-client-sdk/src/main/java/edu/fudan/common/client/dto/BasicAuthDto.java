package edu.fudan.common.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for user login
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasicAuthDto {
    private String username;
    private String password;
    private String verificationCode;
}
