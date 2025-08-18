package edu.fudan.common.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for creating a default user in auth service
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthDto {
    private String userId;
    private String userName;
    private String password;
}
