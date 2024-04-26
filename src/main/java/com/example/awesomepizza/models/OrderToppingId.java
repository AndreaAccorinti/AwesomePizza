package com.example.awesomepizza.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import org.hibernate.Hibernate;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * The OrderToppingId class defines a composite key that uniquely identifies the association
 * between Orders and Toppings in a many-to-many relationship within a relational database.
 * This setup guarantees data integrity and eliminates duplicate entries, facilitating
 * efficient data management and retrieval by treating each orderId and topping combination
 * as a unique entity in the JPA framework.
 */

@Embeddable
public class OrderToppingId implements Serializable {

    /**
     * Serial version UID for serialization.
     */
    @Serial
    private static final long serialVersionUID = 9088621483389033447L;

    /**
     * The order ID part of the composite key.
     */
    @NotNull
    @Column(name = "orderId", nullable = false)
    private Integer orderId;

    /**
     * The topping part of the composite key.
     */
    @NotNull
    @Column(name = "topping", nullable = false)
    private Integer topping;

    /**
     * Gets the order ID.
     *
     * @return the order ID
     */
    public Integer getOrderId() {
        return orderId;
    }

    /**
     * Sets the order ID.
     *
     * @param orderId the new order ID
     */
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    /**
     * Gets the topping.
     *
     * @return the topping
     */
    public Integer getTopping() {
        return topping;
    }

    /**
     * Sets the topping.
     *
     * @param topping the new topping
     */
    public void setTopping(Integer topping) {
        this.topping = topping;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param o the reference object with which to compare
     * @return true if this object is the same as the obj argument; false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        OrderToppingId entity = (OrderToppingId) o;
        return Objects.equals(this.orderId, entity.orderId) &&
                Objects.equals(this.topping, entity.topping);
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(orderId, topping);
    }

}
