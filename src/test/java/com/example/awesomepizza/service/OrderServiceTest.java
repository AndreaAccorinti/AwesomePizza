package com.example.awesomepizza.service;

import com.example.awesomepizza.models.Order;
import com.example.awesomepizza.models.OrderTopping;
import com.example.awesomepizza.repository.OrderRepository;
import com.example.awesomepizza.repository.OrderToppingRepository;
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

/**
 * Tests for {@link OrderService} focusing on validating the functionality for creating, retrieving,
 * and updating orders using mocks for dependencies like {@link OrderRepository} and {@link OrderToppingRepository}.
 */
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderToppingRepository orderToppingRepository;
    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
    }

    /**
     * Test the placeOrder method to ensure it processes a valid order request correctly.
     * Verifies that an order with the correct properties is created and saved, and all associated toppings
     * are also correctly handled.
     */
    @Test
    void placeOrder_ValidOrderRequest_ReturnsCreatedOrder() {
        // Setup
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setPizzaType("Margherita");
        List<String> toppings = new ArrayList<>();
        toppings.add("Cheese");
        toppings.add("Tomato");
        orderRequest.setToppings(toppings);

        Set<OrderTopping> orderToppings = new HashSet<>();
        for (String topping : toppings) {
            OrderTopping orderTopping = new OrderTopping();
            orderTopping.setToppingName(topping);
            orderToppings.add(orderTopping);
        }

        Order expectedOrder = new Order();
        expectedOrder.setPizzaType("Margherita");
        expectedOrder.setOrderToppings(orderToppings);
        expectedOrder.setStatus("pending");


        when(orderRepository.save(any(Order.class))).thenReturn(expectedOrder);
        when(orderToppingRepository.saveAll(any())).thenReturn(new ArrayList<>(orderToppings));

        // Act
        Order createdOrder = orderService.placeOrder(orderRequest);

        // Assert
        assertNotNull(createdOrder);
        assertEquals("Margherita", createdOrder.getPizzaType());
        assertEquals("pending", createdOrder.getStatus());
        assertEquals(2, createdOrder.getOrderToppings().size());
        verify(orderToppingRepository, times(1)).saveAll(any());
        verify(orderRepository, times(2)).save(any(Order.class));

    }


    /**
     * Test the getOrderById method to retrieve an existing order.
     * Ensures the correct order is returned if it exists.
     */
    @Test
    void getOrderById_OrderExists_ReturnsOrder() {
        Integer orderId = 1;
        Order expectedOrder = new Order();
        expectedOrder.setOrderId(orderId);
        expectedOrder.setPizzaType("Margherita");
        expectedOrder.setStatus("pending");

        when(orderRepository.findByOrderId(orderId)).thenReturn(java.util.Optional.of(expectedOrder));

        Order foundOrder = orderService.getOrderById(orderId);

        assertNotNull(foundOrder);
        assertEquals(orderId, foundOrder.getOrderId());
        assertEquals("Margherita", foundOrder.getPizzaType());
        assertEquals("pending", foundOrder.getStatus());

        verify(orderRepository, times(1)).findByOrderId(orderId);
    }

    /**
     * Test the getOrderById method to check behavior when no order exists for a given ID.
     * Ensures null is returned.
     */
    @Test
    void getOrderById_OrderDoesNotExist_ReturnsNull() {
        Integer orderId = 1;

        when(orderRepository.findByOrderId(orderId)).thenReturn(java.util.Optional.empty());

        Order foundOrder = orderService.getOrderById(orderId);

        assertNull(foundOrder);
        verify(orderRepository, times(1)).findByOrderId(orderId);
    }

    /**
     * Test the updateOrderStatus method to ensure it updates the status of an existing order.
     * Verifies the method returns true and the order's status is updated successfully.
     */
    @Test
    void updateOrderStatus_OrderExists_StatusUpdatedSuccessfully_ReturnsTrue() {
        Integer orderId = 1;
        UpdateOrderStatusRequest updateOrderStatusRequest = new UpdateOrderStatusRequest();
        updateOrderStatusRequest.setStatus("ready");

        Order existingOrder = new Order();
        existingOrder.setOrderId(orderId);
        existingOrder.setPizzaType("Margherita");
        existingOrder.setStatus("pending");

        when(orderRepository.findByOrderId(orderId)).thenReturn(java.util.Optional.of(existingOrder));
        when(orderRepository.save(any(Order.class))).thenReturn(existingOrder);

        boolean statusUpdated = orderService.updateOrderStatus(orderId, updateOrderStatusRequest);

        assertTrue(statusUpdated);
        assertEquals("ready", existingOrder.getStatus());

        verify(orderRepository, times(1)).findByOrderId(orderId);
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    /**
     * Test the updateOrderStatus method to check behavior when no order exists for the given ID.
     * Verifies the method returns false.
     */
    @Test
    void updateOrderStatus_OrderDoesNotExist_ReturnsFalse() {
        Integer orderId = 1;
        UpdateOrderStatusRequest updateOrderStatusRequest = new UpdateOrderStatusRequest();
        updateOrderStatusRequest.setStatus("ready");

        when(orderRepository.findByOrderId(orderId)).thenReturn(java.util.Optional.empty());

        boolean statusUpdated = orderService.updateOrderStatus(orderId, updateOrderStatusRequest);

        assertFalse(statusUpdated);
        verify(orderRepository, times(1)).findByOrderId(orderId);
        verify(orderRepository, never()).save(any());
    }

    /**
     * Test the getFirstPendingOrder method to verify it returns a pending order when one exists.
     */
    @Test
    void getFirstPendingOrder_PendingOrderExists_ReturnsOrder() {
        Order pendingOrder = new Order();
        pendingOrder.setOrderId(1);
        pendingOrder.setPizzaType("Margherita");
        pendingOrder.setStatus("pending");

        when(orderRepository.findFirstByStatusPending()).thenReturn(java.util.Optional.of(pendingOrder));

        Order foundOrder = orderService.getFirstPendingOrder();

        assertNotNull(foundOrder);
        assertEquals(pendingOrder, foundOrder);

        verify(orderRepository, times(1)).findFirstByStatusPending();
    }

    /**
     * Test the getFirstPendingOrder method to check behavior when no pending orders exist.
     * Ensures the method returns null.
     */
    @Test
    void getFirstPendingOrder_NoPendingOrder_ReturnsNull() {
        when(orderRepository.findFirstByStatusPending()).thenReturn(java.util.Optional.empty());

        Order foundOrder = orderService.getFirstPendingOrder();

        assertNull(foundOrder);

        verify(orderRepository, times(1)).findFirstByStatusPending();
    }
}
