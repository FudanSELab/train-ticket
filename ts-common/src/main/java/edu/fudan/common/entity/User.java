package edu.fudan.common.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * @author fdse
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private UUID userId;
    private String userName;
    private String password;

    private int gender;

    private int documentType;

    private String documentNum;

    private String email;

}
