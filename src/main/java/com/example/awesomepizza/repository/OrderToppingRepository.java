package com.example.awesomepizza.repository;

import com.example.awesomepizza.models.OrderTopping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for managing {@link OrderTopping} entities, offering standard CRUD operations
 * on the 'order_toppings' table through Spring Data JPA's JpaRepository.
 */

@Repository
public interface OrderToppingRepository extends JpaRepository<OrderTopping, Integer> {
}
