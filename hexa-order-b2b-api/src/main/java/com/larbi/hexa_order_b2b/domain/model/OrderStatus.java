package com.larbi.hexa_order_b2b.domain.model;

/**
 * Represents the different statuses an order can have throughout its lifecycle.
 * The order progresses through these states:
 * - {@code CREATED}: The order has been created but not yet confirmed.
 * - {@code CONFIRMED}: The order has been confirmed by the client.
 * - {@code PAID}: The order has been paid.
 * - {@code SHIPPED}: The order has been shipped to the client.
 * - {@code CANCELLED}: The order has been cancelled and will not proceed further.
 */
public enum OrderStatus {
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

