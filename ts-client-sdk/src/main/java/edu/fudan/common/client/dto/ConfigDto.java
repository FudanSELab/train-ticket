package edu.fudan.common.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for Config entity exchanged with config service.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfigDto {
    private String name;
    private String value;
    private String description;
}
