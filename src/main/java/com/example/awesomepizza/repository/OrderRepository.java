package com.example.awesomepizza.repository;

import com.example.awesomepizza.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, String>, JpaSpecificationExecutor<Order> {

    /**
     * Finds the first order with the status 'pending'.
     * This method is primarily for the Pizzaiolo to get the next order to be processed.
     *
     * @return An Optional containing the first 'pending' order if found, or an empty Optional otherwise.
     */
    @Query("SELECT order FROM Order order WHERE order.status = 'pending' ORDER BY order.orderId ASC LIMIT 1")
    Optional<Order> findFirstByStatusPending();

    /**
     * Retrieves an order by its unique identifier.
     * This method is useful for customers wanting to check the status of their order.
     *
     * @param orderId The unique identifier of the order to find.
     * @return An Optional containing the order if found, or an empty Optional if no order matches the given identifier.
     */
    Optional<Order> findByOrderId(@Param("orderId") Integer orderId);
}
