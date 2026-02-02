package com.larbi.hexa_order_b2b.infrastructure.persistence;

import com.larbi.hexa_order_b2b.domain.model.OrderStatus;
import com.larbi.hexa_order_b2b.domain.model.OrderItem;
import com.larbi.hexa_order_b2b.domain.model.Money;
import com.larbi.hexa_order_b2b.domain.model.Order;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.UUID;

class OrderMapperTest {

    @Test
    void toEntity_shouldMapAllFieldsAndItems_andSetItemOrderReference() {
        UUID orderId = UUID.randomUUID();
        UUID clientId = UUID.randomUUID();
        Order order = Order.restore(
                orderId,
                clientId,
                OrderStatus.CONFIRMED,
                new Money(new BigDecimal("99.90"))
        );
        UUID p1 = UUID.randomUUID();
        UUID p2 = UUID.randomUUID();
        order.restoreItem(new OrderItem(p1, 2, new Money(new BigDecimal("10.00"))));
        order.restoreItem(new OrderItem(p2, 1, new Money(new BigDecimal("79.90"))));
        OrderEntity entity = OrderMapper.toEntity(order);
        assertEquals(orderId, entity.getId());
        assertEquals(clientId, entity.getClientId());
        assertEquals(OrderStatusEntity.CONFIRMED, entity.getStatus());
        assertEquals(new BigDecimal("99.90"), entity.getTotal());
        assertEquals(2, entity.getItems().size());
        entity.getItems().forEach(i -> assertSame(entity, i.getOrder()));
        OrderItemEntity i1 = entity.getItems().get(0);
        assertNotNull(i1.getProductId());
        assertTrue(i1.getQuantity() > 0);
        assertNotNull(i1.getUnitPrice());
    }

    @Test
    void toDomain_shouldMapAllFieldsAndItems() {
        UUID orderId = UUID.randomUUID();
        UUID clientId = UUID.randomUUID();
        OrderEntity entity = new OrderEntity();
        entity.setId(orderId);
        entity.setClientId(clientId);
        entity.setStatus(OrderStatusEntity.SHIPPED);
        entity.setTotal(new BigDecimal("50.00"));
        OrderItemEntity item1 = new OrderItemEntity();
        item1.setProductId(UUID.randomUUID());
        item1.setQuantity(3);
        item1.setUnitPrice(new BigDecimal("10.00"));
        OrderItemEntity item2 = new OrderItemEntity();
        item2.setProductId(UUID.randomUUID());
        item2.setQuantity(2);
        item2.setUnitPrice(new BigDecimal("10.00"));
        entity.addItem(item1);
        entity.addItem(item2);
        Order order = OrderMapper.toDomain(entity);
        assertEquals(orderId, order.getId());
        assertEquals(clientId, order.getClientId());
        assertEquals(OrderStatus.SHIPPED, order.getStatus());
        assertEquals(new BigDecimal("50.00"), order.getTotal().getAmount());
        assertEquals(2, order.getItems().size());
        assertEquals(item1.getProductId(), order.getItems().getFirst().getProductId());
        assertEquals(item1.getQuantity(), order.getItems().getFirst().getQuantity());
        assertEquals(item1.getUnitPrice(), order.getItems().getFirst().getUnitPrice().getAmount());
    }
}
