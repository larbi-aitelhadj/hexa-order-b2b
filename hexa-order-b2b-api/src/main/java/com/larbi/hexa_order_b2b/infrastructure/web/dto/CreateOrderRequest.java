package com.larbi.hexa_order_b2b.infrastructure.web.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

/**
 * Data Transfer Object (DTO) for creating a new order.
 * This record is used to receive the necessary data for creating an order,
 * including the client ID and the list of items to be added to the order.
 * It includes validation annotations to ensure the input data is valid.
 */
public record CreateOrderRequest(

        /**
         * The unique identifier of the client who is placing the order.
         * This field is required and cannot be null.
         */
        @NotNull(message = "clientId is required")
        UUID clientId,

        /**
         * A list of items to be included in the order.
         * This list must not be empty.
         */
        @NotEmpty(message = "items cannot be empty")
        List<AddItemRequest> items

) {}
