package com.larbi.hexa_order_b2b.infrastructure.web.controller;

import com.larbi.hexa_order_b2b.application.usecase.OrderApplicationService;
import com.larbi.hexa_order_b2b.infrastructure.web.dto.AddItemRequest;
import com.larbi.hexa_order_b2b.infrastructure.web.dto.CreateOrderRequest;
import com.larbi.hexa_order_b2b.infrastructure.web.dto.OrderResponse;
import com.larbi.hexa_order_b2b.infrastructure.web.mapper.OrderWebMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

/**
 * REST controller for managing orders.
 * Provides endpoints to create, retrieve, update, and confirm orders.
 * The controller interacts with the {@link OrderApplicationService} to handle business logic.
 */
@RestController
@RequestMapping("/orders")
public class OrderController {

    /** The service used for handling order-related business logic. */
    private final OrderApplicationService service;

    /**
     * Constructs an {@code OrderController} with the specified {@link OrderApplicationService}.
     *
     * @param service the service used to handle order-related operations
     */
    public OrderController(OrderApplicationService service) {
        this.service = service;
    }

    /**
     * Retrieves all orders in the system.
     *
     * @return a list of all orders as {@link OrderResponse} objects
     */
    @GetMapping
    public List<OrderResponse> getAll() {
        return service.getAllOrders()
                .stream()
                .map(OrderWebMapper::toResponse)
                .toList();
    }

    /**
     * Creates a new order based on the provided {@link CreateOrderRequest}.
     *
     * @param request the request body containing order creation data
     * @return the unique identifier of the newly created order
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UUID create(@Valid @RequestBody CreateOrderRequest request) {
        return service.createOrder(
                request.clientId(),
                request.items()
        );
    }

    /**
     * Adds a new item to an existing order.
     *
     * @param id the unique identifier of the order to add the item to
     * @param request the request body containing item data to be added
     */
    @PostMapping("/{id}/items")
    public void addItem(
            @PathVariable UUID id,
            @Valid @RequestBody AddItemRequest request
    ) {
        service.addItem(
                id,
                request.productId(),
                request.quantity(),
                request.unitPrice()
        );
    }

    /**
     * Confirms an order, transitioning it to the confirmed state.
     *
     * @param id the unique identifier of the order to be confirmed
     */
    @PostMapping("/{id}/confirm")
    public void confirm(@PathVariable UUID id) {
        service.confirm(id);
    }

    /**
     * Retrieves an order by its unique identifier.
     *
     * @param id the unique identifier of the order to retrieve
     * @return the order as an {@link OrderResponse} object
     */
    @GetMapping("/{id}")
    public OrderResponse getById(@PathVariable UUID id) {
        return OrderWebMapper.toResponse(
                service.getById(id)
        );
    }

}
