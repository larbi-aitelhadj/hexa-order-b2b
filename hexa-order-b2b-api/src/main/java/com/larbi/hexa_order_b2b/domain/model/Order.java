package com.larbi.hexa_order_b2b.domain.model;

import com.larbi.hexa_order_b2b.domain.exception.OrderDomainException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Represents an order made by a client, including details about the order status,
 * items, and total price. The order goes through various states such as CREATED,
 * CONFIRMED, PAID, SHIPPED, and CANCELLED.
 */
public class Order {

    /** The unique identifier for the order. */
    private final UUID id;

    /** The unique identifier of the client who placed the order. */
    private final UUID clientId;

    /** The current status of the order. */
    private OrderStatus status;

    /** The list of items included in the order. */
    private final List<OrderItem> items;

    /** The total price of the order. */
    private Money total;

    /**
     * Private constructor to create an order with a specified ID and client ID.
     * Initializes the order with a CREATED status and an empty item list.
     *
     * @param id the unique identifier for the order
     * @param clientId the unique identifier of the client placing the order
     */
    private Order(UUID id, UUID clientId) {
        this.id = id;
        this.clientId = clientId;
        this.status = OrderStatus.CREATED;
        this.items = new ArrayList<>();
        this.total = Money.zero();
    }

    /**
     * Creates a new order with a unique ID and the specified client ID.
     * The order is initially in the CREATED status.
     *
     * @param clientId the unique identifier of the client placing the order
     * @return a newly created order with a random ID
     */
    public static Order create(UUID clientId) {
        return new Order(UUID.randomUUID(), clientId);
    }

    /**
     * Restores an existing order using the provided details.
     * This method is typically used when reloading the order from a data source.
     *
     * @param id the unique identifier of the order
     * @param clientId the unique identifier of the client who placed the order
     * @param status the current status of the order
     * @param total the total price of the order
     * @return a restored order with the specified details
     */
    public static Order restore(
            UUID id,
            UUID clientId,
            OrderStatus status,
            Money total
    ) {
        Order order = new Order(id, clientId);
        order.status = status;
        order.total = total;
        return order;
    }

    /**
     * Adds an item to the order. The order must be in the CREATED state to add items.
     *
     * @param item the item to be added to the order
     * @throws OrderDomainException if the order is no longer in the CREATED state
     */
    public void addItem(OrderItem item) {
        if (status != OrderStatus.CREATED) {
            throw new OrderDomainException("Cannot modify order after confirmation");
        }
        items.add(item);
        recalculateTotal();
    }

    /**
     * Adds an item to the order. This method is used for restoring items in infrastructure scenarios.
     *
     * @param item the item to be added to the order
     */
    // Infra only
    public void restoreItem(OrderItem item) {
        items.add(item);
        recalculateTotal();
    }

    /**
     * Confirms the order. The order must be in the CREATED state before it can be confirmed.
     *
     * @throws OrderDomainException if the order is not in the CREATED state
     */
    public void confirm() {
        if (status != OrderStatus.CREATED) {
            throw new OrderDomainException("Order can only be confirmed from CREATED state");
        }
        status = OrderStatus.CONFIRMED;
    }

    /**
     * Marks the order as paid. The order must be in the CONFIRMED state before it can be paid.
     *
     * @throws OrderDomainException if the order is not in the CONFIRMED state
     */
    public void pay() {
        if (status != OrderStatus.CONFIRMED) {
            throw new OrderDomainException("Order must be CONFIRMED before payment");
        }
        status = OrderStatus.PAID;
    }

    /**
     * Marks the order as shipped. The order must be in the PAID state before it can be shipped.
     *
     * @throws OrderDomainException if the order is not in the PAID state
     */
    public void ship() {
        if (status != OrderStatus.PAID) {
            throw new OrderDomainException("Order must be PAID before shipping");
        }
        status = OrderStatus.SHIPPED;
    }

    /**
     * Cancels the order. The order can only be cancelled if it is not in the PAID or SHIPPED state.
     *
     * @throws OrderDomainException if the order is already PAID or SHIPPED
     */
    public void cancel() {
        if (status == OrderStatus.PAID || status == OrderStatus.SHIPPED) {
            throw new OrderDomainException("Paid or shipped order cannot be cancelled");
        }
        status = OrderStatus.CANCELLED;
    }

    /**
     * Recalculates the total price of the order by summing the total prices of all order items.
     */
    private void recalculateTotal() {
        this.total = items.stream()
                .map(OrderItem::totalPrice)
                .reduce(Money.zero(), Money::add);
    }

    /**
     * Gets the unique identifier of the order.
     *
     * @return the unique identifier of the order
     */
    public UUID getId() { return id; }

    /**
     * Gets the unique identifier of the client who placed the order.
     *
     * @return the unique identifier of the client
     */
    public UUID getClientId() { return clientId; }

    /**
     * Gets the current status of the order.
     *
     * @return the current status of the order
     */
    public OrderStatus getStatus() { return status; }

    /**
     * Gets the total price of the order.
     *
     * @return the total price of the order
     */
    public Money getTotal() { return total; }

    /**
     * Gets the list of items in the order.
     *
     * @return an immutable list of order items
     */
    public List<OrderItem> getItems() {
        return List.copyOf(items);
    }
}
