package edu.fudan.common.client.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Wait list order DTO mirroring {@code order.entity.WaitListOrder}.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WaitListOrderDto {

    /**
     * Unique identifier of the wait list order.
     */
    private String id;

    /**
     * Planned departure time (ISO string).
     */
    private String travelTime;

    /**
     * User identifier associated with the request.
     */
    private String userId;

    /**
     * Contacts identifier used for the wait list submission.
     */
    private String contactsId;

    /**
     * Passenger contact name.
     */
    private String contactsName;

    /**
     * Passenger certificate type.
     */
    private int contactsDocumentType;

    /**
     * Passenger certificate number.
     */
    private String contactsDocumentNumber;

    /**
     * Desired train number.
     */
    private String trainNumber;

    /**
     * Preferred seat type code.
     */
    private int seatType;

    /**
     * Origin station name.
     */
    private String fromStation;

    /**
     * Destination station name.
     */
    private String toStation;

    /**
     * Expected ticket price.
     */
    private String price;

    /**
     * Deadline for the wait (formatted date).
     */
    private String waitUtilTime;

    /**
     * Creation timestamp (formatted date).
     */
    private String createdTime;

    /**
     * Current wait list order status.
     */
    private int status;
}


