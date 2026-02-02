package com.larbi.hexa_order_b2b.domain.model;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Represents an immutable monetary value with a specific amount.
 * This class provides methods to create, add, and compare monetary amounts.
 */
public final class Money {

    /** The amount of money, represented as a {@link BigDecimal}. */
    private final BigDecimal amount;

    /**
     * Constructs a {@code Money} instance with the specified amount.
     *
     * @param amount the monetary amount (must be positive and non-null)
     * @throws IllegalArgumentException if the {@code amount} is null or negative
     */
    public Money(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        this.amount = amount;
    }

    /**
     * Returns a {@code Money} instance representing zero currency.
     *
     * @return a {@code Money} instance with zero value
     */
    public static Money zero() {
        return new Money(BigDecimal.ZERO);
    }

    /**
     * Adds the specified {@code Money} instance to this one.
     *
     * @param other the {@code Money} instance to be added
     * @return a new {@code Money} instance representing the sum of the two amounts
     */
    public Money add(Money other) {
        return new Money(this.amount.add(other.amount));
    }

    /**
     * Retrieves the amount of money as a {@link BigDecimal}.
     *
     * @return the amount of money
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Compares this {@code Money} instance with another object for equality.
     * Two {@code Money} instances are considered equal if their amounts are equal.
     *
     * @param o the object to be compared with
     * @return {@code true} if the specified object is equal to this {@code Money} instance,
     *         otherwise {@code false}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Money money)) return false;
        return amount.compareTo(money.amount) == 0;
    }

    /**
     * Returns the hash code for this {@code Money} instance.
     *
     * @return the hash code value for this instance
     */
    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }
}

