package com.larbi.hexa_order_b2b.infrastructure.web.mapper;

import com.larbi.hexa_order_b2b.domain.model.Money;
import com.larbi.hexa_order_b2b.domain.model.Order;
import com.larbi.hexa_order_b2b.domain.model.OrderItem;
import com.larbi.hexa_order_b2b.domain.model.OrderStatus;
import com.larbi.hexa_order_b2b.infrastructure.web.dto.OrderItemResponse;
import com.larbi.hexa_order_b2b.infrastructure.web.dto.OrderResponse;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.UUID;

class OrderWebMapperTest {

    @Test
    void toResponse_shouldMapBasicFields() {
        UUID orderId = UUID.randomUUID();
        UUID clientId = UUID.randomUUID();
        Order order = Order.restore(
                orderId,
                clientId,
                OrderStatus.CREATED,
                new Money(new BigDecimal("123.45"))
        );
        OrderResponse response = OrderWebMapper.toResponse(order);
        assertEquals(orderId, response.id());
        assertEquals(clientId, response.clientId());
        assertEquals("CREATED", response.status());
        assertEquals(new BigDecimal("123.45"), response.total());
        assertNotNull(response.items());
        assertTrue(response.items().isEmpty());
    }

    @Test
    void toResponse_shouldMapItems() {
        // given
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
        OrderResponse response = OrderWebMapper.toResponse(order);
        assertEquals(orderId, response.id());
        assertEquals(clientId, response.clientId());
        assertEquals("CONFIRMED", response.status());
        assertEquals(new BigDecimal("99.90"), response.total());
        assertEquals(2, response.items().size());
        OrderItemResponse r1 = response.items().getFirst();
        assertEquals(p1, r1.productId());
        assertEquals(2, r1.quantity());
        assertEquals(new BigDecimal("10.00"), r1.unitPrice());
        OrderItemResponse r2 = response.items().get(1);
        assertEquals(p2, r2.productId());
        assertEquals(1, r2.quantity());
        assertEquals(new BigDecimal("79.90"), r2.unitPrice());
    }

    @Test
    void toResponse_shouldUseEnumNameForStatus() {
        Order order = Order.restore(
                UUID.randomUUID(),
                UUID.randomUUID(),
                OrderStatus.SHIPPED,
                new Money(new BigDecimal("1.00"))
        );
        OrderResponse response = OrderWebMapper.toResponse(order);
        assertEquals("SHIPPED", response.status());
    }

    @Test
    void toResponse_withNullOrder_shouldThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> OrderWebMapper.toResponse(null));
    }
}
