package com.larbi.hexa_order_b2b.domain.model;

import com.larbi.hexa_order_b2b.domain.exception.OrderDomainException;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.UUID;

class OrderTest {

    @Test
    void should_create_order_with_created_status() {
        Order order = Order.create(UUID.randomUUID());

        assertEquals(OrderStatus.CREATED, order.getStatus());
        assertEquals(BigDecimal.ZERO, order.getTotal().getAmount());
    }

    @Test
    void should_add_item_and_recalculate_total() {
        Order order = Order.create(UUID.randomUUID());

        OrderItem item = new OrderItem(
                UUID.randomUUID(),
                2,
                new Money(BigDecimal.valueOf(50))
        );

        order.addItem(item);

        assertEquals(1, order.getItems().size());
        assertEquals(BigDecimal.valueOf(100), order.getTotal().getAmount());
    }

    @Test
    void should_not_allow_add_item_after_confirmation() {
        Order order = Order.create(UUID.randomUUID());
        order.confirm();

        OrderItem item = new OrderItem(
                UUID.randomUUID(),
                1,
                new Money(BigDecimal.TEN)
        );

        assertThrows(OrderDomainException.class, () -> order.addItem(item));
    }

    @Test
    void should_confirm_order() {
        Order order = Order.create(UUID.randomUUID());

        order.confirm();

        assertEquals(OrderStatus.CONFIRMED, order.getStatus());
    }

    @Test
    void should_pay_confirmed_order() {
        Order order = Order.create(UUID.randomUUID());
        order.confirm();

        order.pay();

        assertEquals(OrderStatus.PAID, order.getStatus());
    }

    @Test
    void should_ship_paid_order() {
        Order order = Order.create(UUID.randomUUID());
        order.confirm();
        order.pay();

        order.ship();

        assertEquals(OrderStatus.SHIPPED, order.getStatus());
    }

    @Test
    void should_not_pay_unconfirmed_order() {
        Order order = Order.create(UUID.randomUUID());

        assertThrows(OrderDomainException.class, order::pay);
    }

    @Test
    void should_cancel_created_order() {
        Order order = Order.create(UUID.randomUUID());

        order.cancel();

        assertEquals(OrderStatus.CANCELLED, order.getStatus());
    }

    @Test
    void should_not_cancel_paid_order() {
        Order order = Order.create(UUID.randomUUID());
        order.confirm();
        order.pay();

        assertThrows(OrderDomainException.class, order::cancel);
    }
}
