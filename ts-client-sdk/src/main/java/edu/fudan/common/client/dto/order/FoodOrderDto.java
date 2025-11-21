package edu.fudan.common.client.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Food order DTO mirroring {@code order.entity.FoodOrder}.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodOrderDto {

    /**
     * Unique identifier of the food order record.
     */
    private String id;

    /**
     * Associated ticket order identifier.
     */
    private String orderId;

    /**
     * Food type flag. 1 - train food, 2 - station store.
     */
    private int foodType;

    /**
     * Station name when the order targets a station store.
     */
    private String stationName;

    /**
     * Store name when the order targets a station store.
     */
    private String storeName;

    /**
     * Friendly food name shown to the passenger.
     */
    private String foodName;

    /**
     * Price of the ordered food.
     */
    private double price;
}


