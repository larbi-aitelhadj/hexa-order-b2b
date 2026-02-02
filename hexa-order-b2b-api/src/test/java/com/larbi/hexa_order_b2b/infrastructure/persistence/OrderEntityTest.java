package com.larbi.hexa_order_b2b.infrastructure.persistence;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.UUID;

class OrderEntityTest {

    @Test
    void addItem_shouldAddItemAndSetBackReference() {
        OrderEntity order = new OrderEntity();
        order.setId(UUID.randomUUID());
        order.setClientId(UUID.randomUUID());
        order.setStatus(OrderStatusEntity.CREATED);
        order.setTotal(new BigDecimal("100.00"));
        OrderItemEntity item = new OrderItemEntity();
        item.setProductId(UUID.randomUUID());
        item.setQuantity(2);
        item.setUnitPrice(new BigDecimal("10.00"));
        order.addItem(item);
        assertEquals(1, order.getItems().size());
        assertSame(item, order.getItems().getFirst());
        assertSame(order, item.getOrder(), "OrderItemEntity.order doit pointer vers l'OrderEntity");
    }

}
