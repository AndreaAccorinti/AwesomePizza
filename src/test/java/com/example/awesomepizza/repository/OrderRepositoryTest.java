package com.example.awesomepizza.repository;

import com.example.awesomepizza.models.Order;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class OrderRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        // Set up some data before each test
        Order order1 = new Order();
        order1.setPizzaType("Margherita");
        order1.setStatus("pending");
        orderRepository.save(order1);
        entityManager.persist(order1);

        Order order2 = new Order();
        order2.setPizzaType("Pepperoni");
        order2.setStatus("completed");
        entityManager.persist(order2);

        entityManager.flush();
    }

    @AfterEach
    void tearDown() {
        entityManager.clear();
    }

    @Test
    void findFirstByStatusPending_WhenPendingExists() {
        Optional<Order> foundOrder = orderRepository.findFirstByStatusPending();
        assertTrue(foundOrder.isPresent());
        assertEquals("pending", foundOrder.get().getStatus());
    }

    @Test
    void findFirstByStatusPending_WhenNoPendingExists() {
        entityManager.getEntityManager()
                .createQuery("UPDATE Order o SET o.status = 'completed' WHERE o.status = 'pending'")
                .executeUpdate();
        Optional<Order> foundOrder = orderRepository.findFirstByStatusPending();
        assertFalse(foundOrder.isPresent());
    }

    @Test
    void findById() {
        Order newOrder = new Order();
        newOrder.setPizzaType("Hawaiian");
        newOrder.setStatus("ready");
        entityManager.persist(newOrder);
        entityManager.flush();

        Optional<Order> foundOrder = orderRepository.findById(newOrder.getOrderId());
        assertTrue(foundOrder.isPresent());
        assertEquals("Hawaiian", foundOrder.get().getPizzaType());
    }

    @Test
    void findById_NotFound() {
        Optional<Order> foundOrder = orderRepository.findById(-999); // using a non-existent ID
        assertFalse(foundOrder.isPresent());
    }
}
