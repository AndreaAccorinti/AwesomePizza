package com.example.awesomepizza.request;

/**
 * Represents a request object for updating the status of an order.
 */
public class UpdateOrderStatusRequest {

    private String status;

    /**
     * Gets the new status of the order.
     *
     * @return The new status of the order.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the new status of the order.
     *
     * @param status The new status of the order to set.
     */
    public void setStatus(String status) {
        this.status = status;
    }
}
