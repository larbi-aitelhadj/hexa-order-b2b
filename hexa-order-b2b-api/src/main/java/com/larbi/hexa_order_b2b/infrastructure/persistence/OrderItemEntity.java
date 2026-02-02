package com.larbi.hexa_order_b2b.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * Entity class representing an order item in the database.
 * This class corresponds to the {@code order_items} table and is used for persistence purposes.
 * It is associated with the {@link OrderEntity} class, representing the parent order.
 */
@Entity
@Table(name = "order_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemEntity {

    /** The unique identifier for the order item. */
    @Id
    @GeneratedValue
    private Long id;

    /** The unique identifier for the product associated with the order item. */
    private UUID productId;

    /** The quantity of the product in the order item. */
    private int quantity;

    /** The unit price of the product in the order item. */
    private BigDecimal unitPrice;

    /** The order to which this order item belongs. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;

}
