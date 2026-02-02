package com.larbi.hexa_order_b2b.infrastructure.persistence;

import com.larbi.hexa_order_b2b.domain.model.OrderStatus;
import com.larbi.hexa_order_b2b.domain.model.OrderItem;
import com.larbi.hexa_order_b2b.domain.model.Money;
import com.larbi.hexa_order_b2b.domain.model.Order;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import java.math.BigDecimal;
import java.util.Optional;
import org.mockito.Mock;
import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class JpaOrderRepositoryTest {

    @Mock
    private SpringDataOrderRepository springData;

    @InjectMocks
    private JpaOrderRepository repo;

    @Test
    void save_shouldMapToEntity_callSpringData_save_andReturnMappedDomain() {
        UUID orderId = UUID.randomUUID();
        UUID clientId = UUID.randomUUID();
        Order order = Order.restore(orderId, clientId, OrderStatus.CREATED, new Money(new BigDecimal("20.00")));
        order.restoreItem(new OrderItem(UUID.randomUUID(), 1, new Money(new BigDecimal("20.00"))));
        ArgumentCaptor<OrderEntity> captor = ArgumentCaptor.forClass(OrderEntity.class);
        when(springData.save(any(OrderEntity.class))).thenAnswer(inv -> inv.getArgument(0));
        Order saved = repo.save(order);
        verify(springData).save(captor.capture());
        OrderEntity sent = captor.getValue();
        assertEquals(orderId, sent.getId());
        assertEquals(clientId, sent.getClientId());
        assertEquals(OrderStatusEntity.CREATED, sent.getStatus());
        assertEquals(new BigDecimal("20.00"), sent.getTotal());
        assertEquals(1, sent.getItems().size());
        assertSame(sent, sent.getItems().get(0).getOrder(), "Mapping doit setter la back-reference");
        assertEquals(orderId, saved.getId());
        assertEquals(clientId, saved.getClientId());
        assertEquals(OrderStatus.CREATED, saved.getStatus());
        assertEquals(new BigDecimal("20.00"), saved.getTotal().getAmount());
        assertEquals(1, saved.getItems().size());
    }

    @Test
    void findById_shouldReturnMappedOptional() {
        UUID id = UUID.randomUUID();
        OrderEntity entity = new OrderEntity();
        entity.setId(id);
        entity.setClientId(UUID.randomUUID());
        entity.setStatus(OrderStatusEntity.PAID);
        entity.setTotal(new BigDecimal("10.00"));
        when(springData.findById(id)).thenReturn(Optional.of(entity));
        Optional<Order> result = repo.findById(id);
        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
        assertEquals(OrderStatus.PAID, result.get().getStatus());
        assertEquals(new BigDecimal("10.00"), result.get().getTotal().getAmount());
        verify(springData).findById(id);
    }

    @Test
    void findAll_shouldMapAllEntitiesToDomain() {
        OrderEntity e1 = new OrderEntity();
        e1.setId(UUID.randomUUID());
        e1.setClientId(UUID.randomUUID());
        e1.setStatus(OrderStatusEntity.CREATED);
        e1.setTotal(new BigDecimal("1.00"));
        OrderEntity e2 = new OrderEntity();
        e2.setId(UUID.randomUUID());
        e2.setClientId(UUID.randomUUID());
        e2.setStatus(OrderStatusEntity.SHIPPED);
        e2.setTotal(new BigDecimal("2.00"));
        when(springData.findAll()).thenReturn(List.of(e1, e2));
        List<Order> orders = repo.findAll();
        assertEquals(2, orders.size());
        assertEquals(e1.getId(), orders.get(0).getId());
        assertEquals(OrderStatus.CREATED, orders.get(0).getStatus());
        assertEquals(e2.getId(), orders.get(1).getId());
        assertEquals(OrderStatus.SHIPPED, orders.get(1).getStatus());
        verify(springData).findAll();
    }
}
