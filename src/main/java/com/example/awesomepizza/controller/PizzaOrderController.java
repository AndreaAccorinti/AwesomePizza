package com.example.awesomepizza.controller;

import com.example.awesomepizza.models.Order;
import com.example.awesomepizza.request.UpdateOrderStatusRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.awesomepizza.request.OrderRequest;
import com.example.awesomepizza.service.OrderService;

/**
 * Controller class for managing pizza orders.
 */
@RestController
@RequestMapping("/api")
public class PizzaOrderController {

    private final OrderService orderService;

    @Autowired
    public PizzaOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Endpoint for placing a new pizza order.
     *
     * @param orderRequest The request body containing order details.
     * @return ResponseEntity containing the created order with HTTP status 201 if successful,
     *         or an empty response with HTTP status 400 if the input is invalid.
     */
    @PostMapping("/orders")
    public ResponseEntity<Order> placeOrder(@RequestBody OrderRequest orderRequest) {
        Order createdOrder = orderService.placeOrder(orderRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    /**
     * Endpoint for retrieving order details by ID.
     *
     * @param orderId The ID of the order to retrieve.
     * @return ResponseEntity containing the order details with HTTP status 200 if found,
     *         or an empty response with HTTP status 404 if the order is not found.
     */
    @GetMapping("/orders/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable Integer orderId) {
        Order order = orderService.getOrderById(orderId);
        if (order != null) {
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Endpoint for updating the status of an order.
     *
     * @param orderId                 The ID of the order to update.
     * @param updateOrderStatusRequest The request body containing the updated status.
     * @return ResponseEntity with HTTP status 200 if successful,
     *         or an empty response with HTTP status 404 if the order is not found.
     */
    @PutMapping("/orders/{orderId}")
    public ResponseEntity<Void> updateOrderStatus(@PathVariable Integer orderId, @RequestBody UpdateOrderStatusRequest updateOrderStatusRequest) {
        boolean updated = orderService.updateOrderStatus(orderId, updateOrderStatusRequest);
        if (updated) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Endpoint for retrieving the first pending order.
     *
     * @return ResponseEntity containing the pending order with HTTP status 200 if found,
     *         or an empty response with HTTP status 404 if no pending order is found.
     */
    @GetMapping("/orders")
    public ResponseEntity<Order> getFirstPendingOrder() {
        Order pendingOrder = orderService.getFirstPendingOrder();
        if (pendingOrder != null) {
            return ResponseEntity.ok(pendingOrder);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
