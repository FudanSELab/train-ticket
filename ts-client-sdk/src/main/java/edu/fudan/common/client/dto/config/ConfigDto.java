package edu.fudan.common.client.dto.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for Config entity exchanged with config service.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfigDto {
    private String name;
    private String value;
    private String description;
}
