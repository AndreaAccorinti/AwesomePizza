package com.example.awesomepizza.service;

import com.example.awesomepizza.models.Order;
import com.example.awesomepizza.models.OrderTopping;
import com.example.awesomepizza.repository.OrderRepository;
import com.example.awesomepizza.request.OrderRequest;
import com.example.awesomepizza.request.UpdateOrderStatusRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void placeOrder_ValidOrderRequest_ReturnsCreatedOrder() {
        // Test setup
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setPizzaType("Margherita");
        List<String> toppings = new ArrayList<>();
        toppings.add("Cheese");
        toppings.add("Tomato");
        orderRequest.setToppings(toppings);

        // Create a set of OrderTopping entities for the toppings
        Set<OrderTopping> orderToppings = new HashSet<>();
        for (String topping : toppings) {
            OrderTopping orderTopping = new OrderTopping();
            orderTopping.setToppingName(topping);
            orderToppings.add(orderTopping);
        }

        Order expectedOrder = new Order();
        expectedOrder.setPizzaType("Margherita");
        expectedOrder.setOrderToppings(orderToppings); // Set the order toppings as a set of OrderTopping entities
        expectedOrder.setStatus("pending");

        when(orderRepository.save(any())).thenReturn(expectedOrder);

        // Action
        Order createdOrder = orderService.placeOrder(orderRequest);

        // Verification
        assertNotNull(createdOrder);
        assertEquals("Margherita", createdOrder.getPizzaType());
        assertEquals(orderToppings, createdOrder.getOrderToppings());
        assertEquals("pending", createdOrder.getStatus());

        verify(orderRepository, times(1)).save(any());
    }


    @Test
    void getOrderById_OrderExists_ReturnsOrder() {
        // Test setup
        Integer orderId = 1;
        Order expectedOrder = new Order();
        expectedOrder.setOrderId(orderId);
        expectedOrder.setPizzaType("Margherita");
        expectedOrder.setStatus("pending");

        when(orderRepository.findByOrderId(orderId)).thenReturn(java.util.Optional.of(expectedOrder));

        // Action
        Order foundOrder = orderService.getOrderById(orderId);

        // Verification
        assertNotNull(foundOrder);
        assertEquals(orderId, foundOrder.getOrderId());
        assertEquals("Margherita", foundOrder.getPizzaType());
        assertEquals("pending", foundOrder.getStatus());

        verify(orderRepository, times(1)).findByOrderId(orderId);
    }

    @Test
    void getOrderById_OrderDoesNotExist_ReturnsNull() {
        // Test setup
        Integer orderId = 1;

        when(orderRepository.findByOrderId(orderId)).thenReturn(java.util.Optional.empty());

        // Action
        Order foundOrder = orderService.getOrderById(orderId);

        // Verification
        assertNull(foundOrder);

        verify(orderRepository, times(1)).findByOrderId(orderId);
    }

    @Test
    void updateOrderStatus_OrderExists_StatusUpdatedSuccessfully_ReturnsTrue() {
        // Test setup
        Integer orderId = 1;
        UpdateOrderStatusRequest updateOrderStatusRequest = new UpdateOrderStatusRequest();
        updateOrderStatusRequest.setStatus("ready");

        Order existingOrder = new Order();
        existingOrder.setOrderId(orderId);
        existingOrder.setPizzaType("Margherita");
        existingOrder.setStatus("pending");

        when(orderRepository.findByOrderId(orderId)).thenReturn(java.util.Optional.of(existingOrder));
        when(orderRepository.save(any())).thenReturn(existingOrder);

        // Action
        boolean statusUpdated = orderService.updateOrderStatus(orderId, updateOrderStatusRequest);

        // Verification
        assertTrue(statusUpdated);
        assertEquals("ready", existingOrder.getStatus());

        verify(orderRepository, times(1)).findByOrderId(orderId);
        verify(orderRepository, times(1)).save(any());
    }

    @Test
    void updateOrderStatus_OrderDoesNotExist_ReturnsFalse() {
        // Test setup
        Integer orderId = 1;
        UpdateOrderStatusRequest updateOrderStatusRequest = new UpdateOrderStatusRequest();
        updateOrderStatusRequest.setStatus("ready");

        when(orderRepository.findByOrderId(orderId)).thenReturn(java.util.Optional.empty());

        // Action
        boolean statusUpdated = orderService.updateOrderStatus(orderId, updateOrderStatusRequest);

        // Verification
        assertFalse(statusUpdated);

        verify(orderRepository, times(1)).findByOrderId(orderId);
        verify(orderRepository, never()).save(any());
    }

    @Test
    void getFirstPendingOrder_PendingOrderExists_ReturnsOrder() {
        // Test setup
        Order pendingOrder = new Order();
        pendingOrder.setOrderId(1);
        pendingOrder.setPizzaType("Margherita");
        pendingOrder.setStatus("pending");

        when(orderRepository.findFirstByStatusPending()).thenReturn(java.util.Optional.of(pendingOrder));

        // Action
        Order foundOrder = orderService.getFirstPendingOrder();

        // Verification
        assertNotNull(foundOrder);
        assertEquals(pendingOrder, foundOrder);

        verify(orderRepository, times(1)).findFirstByStatusPending();
    }

    @Test
    void getFirstPendingOrder_NoPendingOrder_ReturnsNull() {
        // Test setup
        when(orderRepository.findFirstByStatusPending()).thenReturn(java.util.Optional.empty());

        // Action
        Order foundOrder = orderService.getFirstPendingOrder();

        // Verification
        assertNull(foundOrder);

        verify(orderRepository, times(1)).findFirstByStatusPending();
    }

}
