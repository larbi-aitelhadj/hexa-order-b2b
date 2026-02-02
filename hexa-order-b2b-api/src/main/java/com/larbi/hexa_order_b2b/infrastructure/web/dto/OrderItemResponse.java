package com.larbi.hexa_order_b2b.infrastructure.web.dto;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Data Transfer Object (DTO) for representing an order item in the response.
 * This record is used to return the details of an order item, including the product ID,
 * quantity, and unit price, when retrieving order information.
 */
public record OrderItemResponse(

        /**
         * The unique identifier of the product in the order item.
         */
        UUID productId,

        /**
         * The quantity of the product in the order item.
         */
        int quantity,

        /**
         * The unit price of the product in the order item.
         */
        BigDecimal unitPrice

) {}
