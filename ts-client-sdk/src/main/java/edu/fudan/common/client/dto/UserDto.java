package edu.fudan.common.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * DTO for auth user information
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String userId;
    private String username;
    private Set<String> roles = new HashSet<>();
}
