package com.larbi.hexa_order_b2b.infrastructure.web.mapper;

import com.larbi.hexa_order_b2b.domain.model.Order;
import com.larbi.hexa_order_b2b.infrastructure.web.dto.*;

/**
 * Utility class for mapping between the domain model {@link Order} and the corresponding web layer DTOs.
 * This class provides methods to convert an {@link Order} domain object into a response DTO, which is returned
 * to the client via the API.
 */
public class OrderWebMapper {

    /**
     * Converts an {@link Order} domain model to an {@link OrderResponse} DTO.
     * This method maps the order's details, including the ID, client ID, status, total price, and items,
     * into a response format suitable for the web API.
     *
     * @param order the {@code Order} domain model to convert
     * @return a {@link OrderResponse} DTO containing the order data
     */
    public static OrderResponse toResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getClientId(),
                order.getStatus().name(), // Converts the status enum to a string
                order.getTotal().getAmount(), // Retrieves the total amount from the Money object
                order.getItems().stream() // Maps each order item to an OrderItemResponse DTO
                        .map(i -> new OrderItemResponse(
                                i.getProductId(),
                                i.getQuantity(),
                                i.getUnitPrice().getAmount()
                        ))
                        .toList() // Collects the mapped items into a list
        );
    }

}
