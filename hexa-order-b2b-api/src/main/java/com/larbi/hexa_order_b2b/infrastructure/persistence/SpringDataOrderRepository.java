package com.larbi.hexa_order_b2b.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repository interface for accessing {@link OrderEntity} persistence data.
 * Extends {@link JpaRepository} to provide CRUD operations for the {@code order} table in the database.
 * This repository is used to interact with the `OrderEntity` entities, leveraging Spring Data JPA's
 * automatic implementation of basic data access operations.
 */
@Repository
public interface SpringDataOrderRepository extends JpaRepository<OrderEntity, UUID> {

}
