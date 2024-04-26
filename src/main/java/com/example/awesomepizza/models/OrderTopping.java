package com.example.awesomepizza.models;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * Represents a single topping associated with a specific order.
 * This class is part of the "Awesome Pizza" order management system,
 * linking toppings to their respective orders.
 */
@Entity
@Table(name = "order_toppings")
public class OrderTopping {

    /**
     * Composite key for OrderTopping, encapsulating order ID and topping details.
     */
    @EmbeddedId
    private OrderToppingId id;
private String toppingName;

    /**
     * The order this topping is associated with.
     * It uses a many-to-one relationship as multiple toppings can be linked to one order.
     * The relationship includes a cascade delete operation that ensures that all associated OrderToppings
     * are automatically deleted when an Order is deleted, maintaining data integrity.
     */
    @MapsId("orderId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "orderId", nullable = false)
    private Order order;

    /**
     * Retrieves the composite key for this order topping.
     *
     * @return the embedded ID representing this order topping
     */
    public OrderToppingId getId() {
        return id;
    }

    /**
     * Sets the composite key for this order topping.
     *
     * @param id the new embedded ID for this order topping
     */
    public void setId(OrderToppingId id) {
        this.id = id;
    }

    /**
     * Retrieves the order associated with this topping.
     *
     * @return the order to which this topping belongs
     */
    public Order getOrder() {
        return order;
    }

    /**
     * Sets the order associated with this topping.
     * This method also handles the linkage between the order and this topping,
     * updating the foreign key reference accordingly.
     *
     * @param order the order to link to this topping
     */
    public void setOrder(Order order) {
        this.order = order;
    }

    public String getToppingName() {
        return toppingName;
    }

    public void setToppingName(String toppingName) {
        this.toppingName = toppingName;
    }
}
