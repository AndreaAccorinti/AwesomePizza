package com.example.awesomepizza.models;

import jakarta.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Represents an order in the "Awesome Pizza" ordering system.
 * This class is an entity model for storing order data.
 */
@Entity
@Table(name = "orders")
public class Order {

    /**
     * Unique identifier for the order.
     * This ID is generated automatically by the database (auto-increment).
     */
    @Id
    @Column(name = "order_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    /**
     * The type of pizza ordered.
     * This field can store various pizza types as a string.
     */
    @Column(name = "pizza_type")
    private String pizzaType;

    /**
     * The current status of the order.
     * Possible statuses include "pending", "ready", and "completed".
     */
    @Column(name = "status")
    private String status;

    /**
     * A set of toppings associated with the order.
     * Each order can have multiple toppings.
     */
    @OneToMany(mappedBy = "order")
    private Set<OrderTopping> orderToppings = new LinkedHashSet<>();

    /**
     * Returns the unique order ID.
     *
     * @return the order ID
     */
    public Integer getOrderId() {
        return orderId;
    }

    /**
     * Sets the order ID. Usually not called directly as IDs are auto-generated.
     *
     * @param orderId the ID of the order
     */
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    /**
     * Returns the type of pizza ordered.
     *
     * @return the pizza type as a string
     */
    public String getPizzaType() {
        return pizzaType;
    }

    /**
     * Sets the type of pizza for this order.
     *
     * @param pizzaType the type of pizza
     */
    public void setPizzaType(String pizzaType) {
        this.pizzaType = pizzaType;
    }

    /**
     * Gets the current status of the order.
     *
     * @return the status of the order
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of this order.
     *
     * @param status the new status of the order
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Returns the set of toppings added to the pizza in this order.
     *
     * @return the set of toppings
     */
    public Set<OrderTopping> getOrderToppings() {
        return orderToppings;
    }

    /**
     * Sets the toppings for the pizza in this order.
     *
     * @param orderToppings a set of {@link OrderTopping} entities representing toppings
     */
    public void setOrderToppings(Set<OrderTopping> orderToppings) {
        this.orderToppings = orderToppings;
    }
}
