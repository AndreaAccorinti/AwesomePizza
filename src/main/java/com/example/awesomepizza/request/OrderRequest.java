package com.example.awesomepizza.request;

import java.util.List;

/**
 * Request data transfer object for creating a new pizza order.
 * This class captures details about the pizza type and its toppings,
 * which are used in the order placement process.
 */
public class OrderRequest {

    private String pizzaType;
    private List<String> toppings;

    /**
     * Retrieves the type of pizza specified in the order.
     *
     * @return the type of pizza as a String
     */
    public String getPizzaType() {
        return pizzaType;
    }

    /**
     * Specifies the type of pizza for the order.
     *
     * @param pizzaType the type of pizza to be included in the order
     */
    public void setPizzaType(String pizzaType) {
        this.pizzaType = pizzaType;
    }

    /**
     * Retrieves the list of toppings chosen for the pizza.
     *
     * @return a list of toppings as Strings
     */
    public List<String> getToppings() {
        return toppings;
    }

    /**
     * Sets the toppings for the pizza.
     *
     * @param toppings a list of toppings to add to the pizza
     */
    public void setToppings(List<String> toppings) {
        this.toppings = toppings;
    }
}
