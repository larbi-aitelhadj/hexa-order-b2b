package com.larbi.hexa_order_b2b.domain.model;

import com.larbi.hexa_order_b2b.domain.exception.OrderDomainException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class OrderItemTest {

    @Test
    void should_create_order_item_successfully() {
        OrderItem item = new OrderItem(
                UUID.randomUUID(),
                2,
                new Money(BigDecimal.valueOf(10))
        );

        assertEquals(2, item.getQuantity());
    }

    @Test
    void should_throw_exception_when_quantity_is_zero() {
        assertThrows(
                OrderDomainException.class,
                () -> new OrderItem(
                        UUID.randomUUID(),
                        0,
                        new Money(BigDecimal.TEN)
                )
        );
    }

    @Test
    void should_throw_exception_when_product_id_is_null() {
        assertThrows(
                OrderDomainException.class,
                () -> new OrderItem(
                        null,
                        1,
                        new Money(BigDecimal.TEN)
                )
        );
    }

    @Test
    void should_calculate_total_price_correctly() {
        OrderItem item = new OrderItem(
                UUID.randomUUID(),
                3,
                new Money(BigDecimal.valueOf(20))
        );

        Money total = item.totalPrice();

        assertEquals(BigDecimal.valueOf(60), total.getAmount());
    }
}
