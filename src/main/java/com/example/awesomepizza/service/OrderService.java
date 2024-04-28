package com.example.awesomepizza.service;

import com.example.awesomepizza.models.Order;
import com.example.awesomepizza.models.OrderTopping;
import com.example.awesomepizza.repository.OrderRepository;
import com.example.awesomepizza.repository.OrderToppingRepository;
import com.example.awesomepizza.request.OrderRequest;
import com.example.awesomepizza.request.UpdateOrderStatusRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Service for handling operations related to pizza orders.
 * Provides methods for creating, retrieving, and updating pizza orders,
 * leveraging {@link OrderRepository} and {@link OrderToppingRepository} for persistence.
 */
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderToppingRepository orderToppingRepository;

    /**
     * Constructs an instance of OrderService with necessary repository dependencies.
     *
     * @param orderRepository        Repository for accessing and manipulating Order entities.
     * @param orderToppingRepository Repository for accessing and manipulating OrderTopping entities.
     */
    @Autowired
    public OrderService(OrderRepository orderRepository, OrderToppingRepository orderToppingRepository) {
        this.orderRepository = orderRepository;
        this.orderToppingRepository = orderToppingRepository;
    }

    /**
     * Creates and saves a new pizza order based on provided order details.
     * Initializes the order status to "pending" and processes the list of toppings.
     *
     * @param orderRequest The request containing details for the new order.
     * @return The newly created order with toppings saved and linked.
     */
    public Order placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setPizzaType(orderRequest.getPizzaType());
        order.setStatus("pending");

        order = orderRepository.save(order);

        Set<OrderTopping> orderToppings = new HashSet<>();
        for (String topping : orderRequest.getToppings()) {
            OrderTopping orderTopping = new OrderTopping();
            orderTopping.setToppingName(topping);
            orderTopping.setOrder(order);
            orderToppings.add(orderTopping);
        }

        orderToppingRepository.saveAll(orderToppings);
        order.setOrderToppings(orderToppings);
        return orderRepository.save(order);
    }

    /**
     * Retrieves an order by its unique identifier.
     *
     * @param orderId The unique identifier of the order to be retrieved.
     * @return An optional Order if found; otherwise, returns null.
     */
    public Order getOrderById(Integer orderId) {
        return orderRepository.findByOrderId(orderId).orElse(null);
    }

    /**
     * Updates the status of an existing order based on provided status information.
     *
     * @param orderId                  The identifier of the order to update.
     * @param updateOrderStatusRequest The request containing the new status for the order.
     * @return true if the status update is successful, false if the order does not exist.
     */
    public boolean updateOrderStatus(Integer orderId, UpdateOrderStatusRequest updateOrderStatusRequest) {
        Order order = orderRepository.findByOrderId(orderId).orElse(null);
        if (order != null) {
            order.setStatus(updateOrderStatusRequest.getStatus());
            orderRepository.save(order);
            return true;
        }
        return false;
    }

    /**
     * Retrieves the first order marked as "pending" in the system.
     *
     * @return An optional Order if a pending one is found; otherwise, returns null.
     */
    public Order getFirstPendingOrder() {
        return orderRepository.findFirstByStatusPending().orElse(null);
    }
}
