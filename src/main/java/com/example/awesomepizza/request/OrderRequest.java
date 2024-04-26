package com.example.awesomepizza.request;

import java.util.List;

/**
 * Represents a request object for placing a pizza order.
 */
public class OrderRequest {

    private String pizzaType;
    private List<String> toppings;

    /**
     * Gets the type of pizza.
     *
     * @return The type of pizza.
     */
    public String getPizzaType() {
        return pizzaType;
    }

    /**
     * Sets the type of pizza.
     *
     * @param pizzaType The type of pizza to set.
     */
    public void setPizzaType(String pizzaType) {
        this.pizzaType = pizzaType;
    }

    /**
     * Gets the list of toppings.
     *
     * @return The list of toppings.
     */
    public List<String> getToppings() {
        return toppings;
    }

    /**
     * Sets the list of toppings.
     *
     * @param toppings The list of toppings to set.
     */
    public void setToppings(List<String> toppings) {
        this.toppings = toppings;
    }
}
