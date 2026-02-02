package com.larbi.hexa_order_b2b.domain.repository;

import com.larbi.hexa_order_b2b.domain.model.Order;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Interface for the repository that handles operations related to {@link Order} entities.
 * It provides methods for saving, retrieving, and querying orders in a storage medium.
 * Implementations of this interface typically interact with a database or an external storage system.
 */
public interface OrderRepository {

    /**
     * Saves a given {@code Order} to the repository.
     *
     * @param order the order to be saved
     * @return the saved order, possibly with updated fields (e.g., generated ID)
     */
    Order save(Order order);

    /**
     * Finds an {@code Order} by its unique identifier.
     *
     * @param orderId the unique identifier of the order to find
     * @return an {@link Optional} containing the order if found, or {@link Optional#empty()} if not found
     */
    Optional<Order> findById(UUID orderId);

    /**
     * Retrieves all the orders from the repository.
     *
     * @return a list of all orders
     */
    List<Order> findAll();
}
