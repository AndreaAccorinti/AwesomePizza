package com.example.awesomepizza.repository;

import com.example.awesomepizza.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Repository interface for {@link Order} entities.
 * Extends JpaRepository and JpaSpecificationExecutor to provide standard CRUD operations and the ability
 * to execute specification-based queries.
 */
public interface OrderRepository extends JpaRepository<Order, Integer>, JpaSpecificationExecutor<Order> {

    /**
     * Finds the first order with the status 'pending'.
     * This query is used to retrieve the next order that needs to be processed by the pizzaiolo.
     * Orders are sorted by their identifier in ascending order to ensure the oldest orders are processed first.
     *
     * @return An {@link Optional} containing the first 'pending' order if available, otherwise an empty Optional.
     */
    @Query("SELECT order FROM Order order WHERE order.status = 'pending' ORDER BY order.orderId ASC LIMIT 1")
    Optional<Order> findFirstByStatusPending();

    /**
     * Retrieves an order by its unique identifier.
     * This method is primarily used for customers to check the status of their orders,
     * ensuring that they can track the progress of their order preparation and delivery.
     *
     * @param orderId The unique identifier of the order to find.
     * @return An {@link Optional} containing the order if found, otherwise an empty Optional
     * if no order matches the given identifier.
     */
    Optional<Order> findByOrderId(@Param("orderId") Integer orderId);
}
