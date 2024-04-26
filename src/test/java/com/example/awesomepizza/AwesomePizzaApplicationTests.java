package com.example.awesomepizza;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class AwesomePizzaApplicationTests {

    @Test
    void contextLoads() {
        // Assert that the application context is not null
        assertNotNull(AwesomePizzaApplication.class);
    }
}
