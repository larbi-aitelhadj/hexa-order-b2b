package com.larbi.hexa_order_b2b.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Entity class representing an order in the database.
 * This class is used for persistence purposes and corresponds to the {@code orders} table.
 * It is mapped to the {@link com.larbi.hexa_order_b2b.domain.model.Order} domain model using a repository layer.
 */
@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {

    /** The unique identifier of the order. */
    @Id
    private UUID id;

    /** The unique identifier of the client who placed the order. */
    private UUID clientId;

    /** The status of the order, mapped as an {@link OrderStatusEntity}. */
    @Enumerated(EnumType.STRING)
    private OrderStatusEntity status;

    /** The total price of the order. */
    private BigDecimal total;

    /** The list of items in the order. */
    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<OrderItemEntity> items = new ArrayList<>();

    /**
     * Adds an {@code OrderItemEntity} to the order.
     * The order item is automatically linked to this order.
     *
     * @param item the item to be added to the order
     */
    public void addItem(OrderItemEntity item) {
        items.add(item);
        item.setOrder(this); // Ensure the bidirectional relationship is maintained
    }
}
