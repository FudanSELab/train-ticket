package edu.fudan.common.client.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request payload for querying consign orders by user id.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetConsignOrdersByUserIdDto {

    /**
     * String representation of the user identifier.
     */
    private String userId;
}

