package com.larbi.hexa_order_b2b.application.usecase;

import com.larbi.hexa_order_b2b.domain.exception.OrderDomainException;
import com.larbi.hexa_order_b2b.domain.model.Money;
import com.larbi.hexa_order_b2b.domain.model.Order;
import com.larbi.hexa_order_b2b.domain.model.OrderItem;
import com.larbi.hexa_order_b2b.domain.repository.OrderRepository;
import com.larbi.hexa_order_b2b.infrastructure.web.dto.AddItemRequest;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Service class responsible for handling the business logic related to orders.
 * It acts as an intermediary between the order repository and the external application logic,
 * offering methods for creating, modifying, and retrieving orders.
 * This service is typically used to manage the lifecycle of an order.
 */
@Service
public class OrderApplicationService {

    private static final String  ORDER_NOT_FOUND = "Order not found";
    /** The repository used to persist and retrieve orders. */
    private final OrderRepository orderRepository;

    /**
     * Constructs an {@code OrderApplicationService} with the specified {@link OrderRepository}.
     *
     * @param orderRepository the repository used to handle order persistence
     */
    public OrderApplicationService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * Retrieves all orders in the system.
     *
     * @return a list of all orders
     */
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    /**
     * Creates a new order for the specified client and adds items to it.
     *
     * @param clientId the unique identifier of the client placing the order
     * @param items a list of items to be added to the order, each item specified by product ID, quantity, and unit price
     * @return the unique identifier of the newly created order
     */
    public UUID createOrder(UUID clientId, List<AddItemRequest> items) {
        Order order = Order.create(clientId);
        items.forEach(i ->
                order.addItem(
                        new OrderItem(
                                i.productId(),
                                i.quantity(),
                                new Money(i.unitPrice())
                        )
                )
        );
        orderRepository.save(order);
        return order.getId();
    }

    /**
     * Adds a new item to an existing order.
     *
     * @param orderId the unique identifier of the order to which the item will be added
     * @param productId the unique identifier of the product being added
     * @param quantity the quantity of the product to add
     * @param unitPrice the unit price of the product
     * @throws OrderDomainException if the order cannot be found or the operation fails
     */
    public void addItem(UUID orderId, UUID productId, int quantity, BigDecimal unitPrice) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderDomainException(ORDER_NOT_FOUND));

        order.addItem(
                new OrderItem(
                        productId,
                        quantity,
                        new Money(unitPrice)
                )
        );

        orderRepository.save(order);
    }

    /**
     * Confirms an existing order, transitioning it to the CONFIRMED state.
     *
     * @param orderId the unique identifier of the order to be confirmed
     * @throws OrderDomainException if the order cannot be found or is in an invalid state
     */
    public void confirm(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderDomainException(ORDER_NOT_FOUND));

        order.confirm();
        orderRepository.save(order);
    }

    /**
     * Retrieves an order by its unique identifier.
     *
     * @param orderId the unique identifier of the order to retrieve
     * @return the {@code Order} instance
     * @throws EntityNotFoundException if the order cannot be found
     */
    public Order getById(UUID orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException(ORDER_NOT_FOUND));
    }
}
