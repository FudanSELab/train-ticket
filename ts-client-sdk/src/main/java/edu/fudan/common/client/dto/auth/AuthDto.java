package edu.fudan.common.client.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthDto {
    private String userId;
    private String userName;
    private String password;
    @Builder.Default
    private Set<String> roles = new HashSet<>();
}
