package com.larbi.hexa_order_b2b.infrastructure.persistence;

import com.larbi.hexa_order_b2b.domain.repository.OrderRepository;
import com.larbi.hexa_order_b2b.domain.model.Order;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * A JPA implementation of the {@link OrderRepository} interface.
 * This class interacts with the database using Spring Data JPA to perform CRUD operations for the {@link Order} entity.
 * It uses an internal {@link SpringDataOrderRepository} to handle the persistence layer,
 * and maps between the domain model and entity using the {@link OrderMapper}.
 */
@Repository
public class JpaOrderRepository implements OrderRepository {

    /** The Spring Data repository used to interact with the underlying database. */
    private final SpringDataOrderRepository repository;

    /**
     * Constructs a {@code JpaOrderRepository} with the specified {@link SpringDataOrderRepository}.
     *
     * @param repository the Spring Data repository used for data access
     */
    public JpaOrderRepository(SpringDataOrderRepository repository) {
        this.repository = repository;
    }

    /**
     * Saves an {@code Order} to the database.
     *
     * @param order the order to save
     * @return the saved {@code Order}, mapped to the domain model
     */
    @Override
    public Order save(Order order) {
        return OrderMapper.toDomain(
                repository.save(OrderMapper.toEntity(order))
        );
    }

    /**
     * Finds an {@code Order} by its unique identifier.
     *
     * @param orderId the unique identifier of the order to find
     * @return an {@link Optional} containing the order if found, or {@link Optional#empty()} if not found
     */
    @Override
    public Optional<Order> findById(UUID orderId) {
        return repository.findById(orderId)
                .map(OrderMapper::toDomain);
    }

    /**
     * Retrieves all orders from the database.
     *
     * @return a list of all orders, mapped to the domain model
     */
    @Override
    public List<Order> findAll() {
        return repository.findAll()
                .stream()
                .map(OrderMapper::toDomain)
                .toList();
    }
}
