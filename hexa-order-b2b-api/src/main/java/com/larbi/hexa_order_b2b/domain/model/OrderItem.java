package com.larbi.hexa_order_b2b.domain.model;

import com.larbi.hexa_order_b2b.domain.exception.OrderDomainException;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * Represents an item in an order, including the product ID, quantity, and unit price.
 * The item is used to calculate the total price for the order based on the quantity
 * and the unit price of the product.
 */
public class OrderItem {

    /** The unique identifier of the product in the order item. */
    private final UUID productId;

    /** The quantity of the product in the order item. */
    private final int quantity;

    /** The unit price of the product in the order item. */
    private final Money unitPrice;

    /**
     * Constructs an {@code OrderItem} instance with the specified product ID, quantity, and unit price.
     *
     * @param productId the unique identifier of the product
     * @param quantity the quantity of the product in the order item (must be greater than zero)
     * @param unitPrice the unit price of the product (must not be null)
     * @throws OrderDomainException if the {@code productId} is null, the {@code quantity} is less than or equal to zero,
     *         or the {@code unitPrice} is null
     */
    public OrderItem(UUID productId, int quantity, Money unitPrice) {
        if (productId == null) {
            throw new OrderDomainException("ProductId must not be null");
        }
        if (quantity <= 0) {
            throw new OrderDomainException("Quantity must be greater than zero");
        }
        if (unitPrice == null) {
            throw new OrderDomainException("Unit price must not be null");
        }

        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    /**
     * Calculates the total price for this order item by multiplying the unit price by the quantity.
     *
     * @return the total price of the item as a {@code Money} instance
     */
    public Money totalPrice() {
        return new Money(
                unitPrice.getAmount().multiply(BigDecimal.valueOf(quantity))
        );
    }

    /**
     * Gets the unique identifier of the product in the order item.
     *
     * @return the product ID
     */
    public UUID getProductId() {
        return productId;
    }

    /**
     * Gets the quantity of the product in the order item.
     *
     * @return the quantity of the product
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Gets the unit price of the product in the order item.
     *
     * @return the unit price of the product
     */
    public Money getUnitPrice() {
        return unitPrice;
    }
}
