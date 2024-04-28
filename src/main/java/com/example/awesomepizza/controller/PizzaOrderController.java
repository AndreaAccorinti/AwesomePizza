package com.example.awesomepizza.controller;

import com.example.awesomepizza.models.Order;
import com.example.awesomepizza.request.OrderRequest;
import com.example.awesomepizza.request.UpdateOrderStatusRequest;
import com.example.awesomepizza.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing pizza orders. This class handles HTTP requests
 * related to creating, retrieving, and updating orders within the Awesome Pizza ordering system.
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
     * Creates a new pizza order based on the provided details in the request body.
     *
     * @param orderRequest The request body containing the details of the pizza order, such as pizza type and toppings.
     * @return A {@link ResponseEntity} containing the created {@link Order} and HTTP status 201 (Created) if successful,
     *         or HTTP status 400 (Bad Request) if the input validation fails or the request body is incorrect.
     */
    @PostMapping("/orders")
    public ResponseEntity<Order> placeOrder(@RequestBody OrderRequest orderRequest) {
        Order createdOrder = orderService.placeOrder(orderRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    /**
     * Retrieves the details of an existing order by its unique identifier.
     *
     * @param orderId The unique identifier of the order to be retrieved.
     * @return A {@link ResponseEntity} containing the {@link Order} details and HTTP status 200 (OK) if the order is found,
     *         or HTTP status 404 (Not Found) if there is no order with the specified ID.
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
     * Updates the status of an existing order identified by its ID, based on the provided updated status.
     *
     * @param orderId                  The ID of the order whose status is to be updated.
     * @param updateOrderStatusRequest The request body containing the new status to be applied to the order.
     * @return A {@link ResponseEntity} with HTTP status 200 (OK) if the update is successful,
     *         or HTTP status 404 (Not Found) if the order with the specified ID does not exist.
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
     * Retrieves the first order that has a status of 'pending'.
     * This endpoint is typically used to fetch the next order in line that needs processing.
     *
     * @return A {@link ResponseEntity} containing the first pending {@link Order} and HTTP status 200 (OK) if found,
     *         or HTTP status 404 (Not Found) if there are no pending orders available.
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
