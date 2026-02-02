package com.larbi.hexa_order_b2b.infrastructure.web.controller;

import com.larbi.hexa_order_b2b.application.usecase.OrderApplicationService;
import com.larbi.hexa_order_b2b.domain.model.Order;
import com.larbi.hexa_order_b2b.infrastructure.web.dto.AddItemRequest;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@WebMvcTest(OrderController.class)
@AutoConfigureMockMvc(addFilters = false)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderApplicationService orderApplicationService;

    @Test
    void should_return_all_orders() throws Exception {
        when(orderApplicationService.getAllOrders())
                .thenReturn(List.of());

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk());
    }

    @Test
    void should_return_order_by_id() throws Exception {
        UUID orderId = UUID.randomUUID();
        Order order = Order.create(UUID.randomUUID());
        when(orderApplicationService.getById(orderId)).thenReturn(order);
        mockMvc.perform(get("/orders/{id}", orderId)).andExpect(status().isOk());
    }

    @Test
    void should_return_404_when_order_not_found() throws Exception {
        UUID orderId = UUID.randomUUID();

        when(orderApplicationService.getById(orderId))
                .thenThrow(EntityNotFoundException.class);

        mockMvc.perform(get("/orders/{id}", orderId))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_create_order() throws Exception {
        UUID clientId = UUID.randomUUID();
        UUID orderId = UUID.randomUUID();

        String json = """
                        {
                          "clientId": "%s",
                          "items": [
                            {
                              "productId": "%s",
                              "quantity": 2,
                              "unitPrice": 50
                            }
                          ]
                        }
                        """.formatted(clientId, UUID.randomUUID());
        when(orderApplicationService.createOrder(
                eq(clientId),
                anyList()
        )).thenReturn(orderId);
        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    void should_add_item_to_order() throws Exception {
        UUID orderId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();

        String json = """
                        {
                          "productId": "%s",
                          "quantity": 1,
                          "unitPrice": 20
                        }
                        """.formatted(productId);

        mockMvc.perform(post("/orders/{id}/items", orderId)
                        .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());

        verify(orderApplicationService).addItem(
                eq(orderId),
                eq(productId),
                eq(1),
                eq(BigDecimal.valueOf(20))
        );
    }

    @Test
    void should_confirm_order() throws Exception {
        UUID orderId = UUID.randomUUID();
        mockMvc.perform(post("/orders/{id}/confirm", orderId)).andExpect(status().isOk());
        verify(orderApplicationService).confirm(orderId);
    }
}
