package com.larbi.hexa_order_b2b.application.usecase;

import com.larbi.hexa_order_b2b.domain.exception.OrderDomainException;
import com.larbi.hexa_order_b2b.domain.model.Order;
import com.larbi.hexa_order_b2b.domain.model.OrderStatus;
import com.larbi.hexa_order_b2b.domain.repository.OrderRepository;
import com.larbi.hexa_order_b2b.infrastructure.web.dto.AddItemRequest;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class OrderApplicationServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderApplicationService service;

    @Test
    void should_return_all_orders() {
        List<Order> orders = List.of(
                Order.create(UUID.randomUUID()),
                Order.create(UUID.randomUUID())
        );
        when(orderRepository.findAll()).thenReturn(orders);
        List<Order> result = service.getAllOrders();
        assertEquals(2, result.size());
        verify(orderRepository).findAll();
    }

    @Test
    void should_create_order_with_items_and_save_it() {
        UUID clientId = UUID.randomUUID();
        List<AddItemRequest> items = List.of(
                new AddItemRequest(UUID.randomUUID(), 2, BigDecimal.valueOf(50)),
                new AddItemRequest(UUID.randomUUID(), 1, BigDecimal.valueOf(100))
        );
        UUID orderId = service.createOrder(clientId, items);
        assertNotNull(orderId);
        verify(orderRepository).save(any(Order.class));
    }

    @Test
    void should_add_item_to_existing_order() {
        UUID orderId = UUID.randomUUID();
        Order order = Order.create(UUID.randomUUID());
        when(orderRepository.findById(orderId))
                .thenReturn(Optional.of(order));
        service.addItem(
                orderId,
                UUID.randomUUID(),
                2,
                BigDecimal.valueOf(20)
        );
        verify(orderRepository).save(order);
        assertEquals(1, order.getItems().size());
    }

    @Test
    void should_throw_exception_when_adding_item_to_unknown_order() {
        UUID orderId = UUID.randomUUID();
        when(orderRepository.findById(orderId))
                .thenReturn(Optional.empty());
        assertThrows(
                OrderDomainException.class,
                () -> service.addItem(
                        orderId,
                        UUID.randomUUID(),
                        1,
                        BigDecimal.TEN
                )
        );
    }

    @Test
    void should_confirm_existing_order() {
        UUID orderId = UUID.randomUUID();
        Order order = Order.create(UUID.randomUUID());
        when(orderRepository.findById(orderId))
                .thenReturn(Optional.of(order));
        service.confirm(orderId);
        assertEquals(OrderStatus.CONFIRMED, order.getStatus());
        verify(orderRepository).save(order);
    }

    @Test
    void should_return_order_when_found() {
        UUID orderId = UUID.randomUUID();
        Order order = Order.create(UUID.randomUUID());
        when(orderRepository.findById(orderId))
                .thenReturn(Optional.of(order));
        Order result = service.getById(orderId);
        assertEquals(order, result);
    }

    @Test
    void should_throw_exception_when_order_not_found() {
        UUID orderId = UUID.randomUUID();
        when(orderRepository.findById(orderId))
                .thenReturn(Optional.empty());

        assertThrows(
                EntityNotFoundException.class,
                () -> service.getById(orderId)
        );
    }
}
