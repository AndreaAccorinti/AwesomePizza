package com.example.awesomepizza.request;

/**
 * Data transfer object for updating the status of an existing pizza order.
 * This class encapsulates the information needed to change the status of an order,
 * such as moving it from "pending" to "completed".
 */
public class UpdateOrderStatusRequest {

    private String status;

    /**
     * Retrieves the current intended new status for an order. This status
     * can represent different stages of the order's lifecycle, such as "ready", "delivered", or "cancelled".
     *
     * @return the intended new status of the order as a String
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets a new status for an order, reflecting a change in its processing state
     * or its completion state. This method is used to update the order's lifecycle state.
     *
     * @param status the new status to assign to the order, defining its new state
     */
    public void setStatus(String status) {
        this.status = status;
    }
}
