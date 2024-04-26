package com.example.awesomepizza.service;

import com.example.awesomepizza.models.Order;
import com.example.awesomepizza.models.OrderTopping;
import com.example.awesomepizza.repository.OrderRepository;
import com.example.awesomepizza.request.OrderRequest;
import com.example.awesomepizza.request.UpdateOrderStatusRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Service class for managing pizza orders.
 */
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * Places a new pizza order.
     *
     * @param orderRequest The request containing order details.
     * @return The created order.
     */
    public Order placeOrder(OrderRequest orderRequest) {
        // Business logic to create and save the order
        Order order = new Order();
        order.setPizzaType(orderRequest.getPizzaType());

        // Convert list of topping strings to set of OrderTopping entities
        Set<OrderTopping> orderToppings = new HashSet<>();
        for (String topping : orderRequest.getToppings()) {
            OrderTopping orderTopping = new OrderTopping();
            orderTopping.setToppingName(topping);
            orderTopping.setOrder(order); // Set the order for each topping
            orderToppings.add(orderTopping);
        }

        order.setOrderToppings(orderToppings);
        order.setStatus("pending");
        return orderRepository.save(order);
    }

    /**
     * Retrieves an order by its ID.
     *
     * @param orderId The ID of the order to retrieve.
     * @return The order if found, otherwise null.
     */
    public Order getOrderById(Integer orderId) {
        // Business logic to retrieve order by ID
        return orderRepository.findByOrderId(orderId).orElse(null);
    }

    /**
     * Updates the status of an order.
     *
     * @param orderId                 The ID of the order to update.
     * @param updateOrderStatusRequest The request containing the updated status.
     * @return True if the order status is updated successfully, otherwise false.
     */
    public boolean updateOrderStatus(Integer orderId, UpdateOrderStatusRequest updateOrderStatusRequest) {
        Order order = orderRepository.findByOrderId(orderId).orElse(null);
        if (order != null) {
            order.setStatus(String.valueOf(updateOrderStatusRequest.getStatus()));
            orderRepository.save(order);
            return true;
        }
        return false;
    }

    /**
     * Retrieves the first pending order.
     *
     * @return The first pending order if found, otherwise null.
     */
    public Order getFirstPendingOrder() {
        return orderRepository.findFirstByStatusPending().orElse(null);
    }
}
