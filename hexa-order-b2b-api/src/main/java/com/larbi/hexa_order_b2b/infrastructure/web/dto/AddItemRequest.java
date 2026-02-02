package com.larbi.hexa_order_b2b.infrastructure.web.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Data Transfer Object (DTO) for adding an item to an order.
 * This record is used to receive the necessary data for adding an item to an order,
 * including the product ID, quantity, and unit price.
 * It includes validation annotations to ensure that the input is valid.
 */
public record AddItemRequest(

        /**
         * The unique identifier of the product being added to the order.
         * This field is required and cannot be null.
         */
        @NotNull(message = "productId is required")
        UUID productId,

        /**
         * The quantity of the product to add to the order.
         * Must be at least 1.
         */
        @Min(value = 1, message = "quantity must be >= 1")
        int quantity,

        /**
         * The unit price of the product being added to the order.
         * This field is required and must be greater than 0.
         */
        @NotNull(message = "unitPrice is required")
        @DecimalMin(value = "0.01", message = "unitPrice must be > 0")
        BigDecimal unitPrice

) {}
