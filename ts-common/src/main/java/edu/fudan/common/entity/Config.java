package edu.fudan.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author fdse
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Config {

    private String name;

    private String value;

    private String description;

}
