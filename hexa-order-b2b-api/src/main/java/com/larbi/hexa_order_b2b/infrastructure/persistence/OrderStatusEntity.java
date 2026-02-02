package com.larbi.hexa_order_b2b.infrastructure.persistence;

/**
 * Enum representing the different statuses an order can have in the persistence layer.
 * This enum is used to track the state of an order in the database.
 * It corresponds to the {@link com.larbi.hexa_order_b2b.domain.model.OrderStatus} enum in the domain model,
 * but is used specifically for the persistence layer (e.g., database storage).
 */
public enum OrderStatusEntity {
    /** The order has been created but not yet confirmed. */
    CREATED,

    /** The order has been confirmed by the client. */
    CONFIRMED,

    /** The order has been paid. */
    PAID,

    /** The order has been shipped to the client. */
    SHIPPED,

    /** The order has been cancelled and will not proceed. */
    CANCELLED
}
