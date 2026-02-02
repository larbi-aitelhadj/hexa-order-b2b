package com.larbi.hexa_order_b2b.domain.model;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {

    @Test
    void should_create_money_with_positive_amount() {
        Money money = new Money(BigDecimal.valueOf(10));
        assertEquals(BigDecimal.valueOf(10), money.getAmount());
    }

    @Test
    void should_allow_zero_amount() {
        Money money = Money.zero();
        assertEquals(BigDecimal.ZERO, money.getAmount());
    }

    @Test
    void should_throw_exception_when_amount_is_negative() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Money(BigDecimal.valueOf(-1))
        );
    }

    @Test
    void should_add_two_money_values() {
        Money m1 = new Money(BigDecimal.valueOf(10));
        Money m2 = new Money(BigDecimal.valueOf(5));

        Money result = m1.add(m2);

        assertEquals(BigDecimal.valueOf(15), result.getAmount());
    }

    @Test
    void should_consider_equal_money_with_different_scale() {
        Money m1 = new Money(new BigDecimal("10.0"));
        Money m2 = new Money(new BigDecimal("10.00"));

        assertEquals(m1, m2);
    }
}
