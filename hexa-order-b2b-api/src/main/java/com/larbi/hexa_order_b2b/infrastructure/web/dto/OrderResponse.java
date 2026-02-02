package com.larbi.hexa_order_b2b.infrastructure.web.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Data Transfer Object (DTO) for representing an order in the response.
 * This record is used to return the details of an order, including the order ID, client ID,
 * order status, total price, and the list of order items when retrieving order information.
 */
public record OrderResponse(

        /**
         * The unique identifier of the order.
         */
        UUID id,

        /**
         * The unique identifier of the client who placed the order.
         */
        UUID clientId,

        /**
         * The status of the order (e.g., CREATED, CONFIRMED, PAID, etc.).
         */
        String status,

        /**
         * The total price of the order.
         */
        BigDecimal total,

        /**
         * A list of items included in the order.
         */
        List<OrderItemResponse> items

) {}
