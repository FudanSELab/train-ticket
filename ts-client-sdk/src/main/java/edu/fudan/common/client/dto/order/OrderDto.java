package edu.fudan.common.client.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Order data transfer object mirroring the fields exposed by ts-order-service.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderDto {

    /**
     * Globally unique order identifier.
     */
    private String id;

    /**
     * Date when the order was placed (yyyy-MM-dd).
     */
    private String boughtDate;

    /**
     * Travel date for the order (yyyy-MM-dd).
     */
    private String travelDate;

    /**
     * Scheduled departure time (HH:mm).
     */
    private String travelTime;

    /**
     * Account id that placed the order.
     */
    private String userId;

    /**
     * Passenger contact name.
     */
    private String contactsName;

    /**
     * Passenger document type code.
     */
    private int documentType;

    /**
     * Passenger document number.
     */
    private String contactsDocumentNumber;

    /**
     * Train number associated with the order.
     */
    private String trainNumber;

    /**
     * Coach number within the train.
     */
    private int coachNumber;

    /**
     * Seat class code, aligned with SeatClass enum.
     */
    private int seatClass;

    /**
     * Seat number assigned to the passenger.
     */
    private String seatNumber;

    /**
     * Departure station id.
     */
    private String fromStationId;

    /**
     * Destination station id.
     */
    private String toStationId;

    /**
     * Current order status code.
     */
    private int status;

    /**
     * Ticket price as a string (to preserve formatting).
     */
    private String price;
}


