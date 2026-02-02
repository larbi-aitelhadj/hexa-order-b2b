package com.larbi.hexa_order_b2b.infrastructure.persistence;

import com.larbi.hexa_order_b2b.domain.model.Money;
import com.larbi.hexa_order_b2b.domain.model.Order;
import com.larbi.hexa_order_b2b.domain.model.OrderItem;
import com.larbi.hexa_order_b2b.domain.model.OrderStatus;

/**
 * Utility class responsible for mapping between the domain model {@link Order} and its corresponding {@link OrderEntity}.
 * This class provides methods to convert between the domain model and persistence entity, ensuring that data can be saved to
 * and retrieved from the database.
 */
public class OrderMapper {

    /** Private constructor to prevent instantiation of the utility class. */
    private OrderMapper() {}

    /**
     * Converts a {@link Order} domain model into an {@link OrderEntity} persistence entity.
     * This method maps the order fields and its items into the corresponding entity format.
     *
     * @param order the {@code Order} domain model to convert
     * @return the mapped {@code OrderEntity} entity
     */
    public static OrderEntity toEntity(Order order) {

        OrderEntity entity = new OrderEntity();
        entity.setId(order.getId());
        entity.setClientId(order.getClientId());
        entity.setStatus(
                OrderStatusEntity.valueOf(order.getStatus().name()) // Converts the order status to the entity status
        );
        entity.setTotal(order.getTotal().getAmount()); // Sets the total price
        entity.getItems().clear(); // Clears any existing items in the entity

        // Maps each order item to an entity
        for (OrderItem item : order.getItems()) {

            OrderItemEntity itemEntity = new OrderItemEntity();
            itemEntity.setProductId(item.getProductId());
            itemEntity.setQuantity(item.getQuantity());
            itemEntity.setUnitPrice(item.getUnitPrice().getAmount());
            entity.addItem(itemEntity);
        }

        return entity;
    }

    /**
     * Converts an {@link OrderEntity} persistence entity into a {@link Order} domain model.
     * This method maps the entity fields and its associated items into the domain model format.
     *
     * @param entity the {@code OrderEntity} to convert
     * @return the mapped {@code Order} domain model
     */
    public static Order toDomain(OrderEntity entity) {
        Order order = Order.restore(
                entity.getId(),
                entity.getClientId(),
                OrderStatus.valueOf(entity.getStatus().name()), // Converts the entity status to the domain model status
                new Money(entity.getTotal())
        );

        // Maps each order item entity to a domain item
        for (OrderItemEntity i : entity.getItems()) {

            order.restoreItem(
                    new OrderItem(
                            i.getProductId(),
                            i.getQuantity(),
                            new Money(i.getUnitPrice())
                    )
            );
        }

        return order;
    }
}
