package com.example.demopocreactive;

import com.example.demopocreactive.configuration.MyContainersConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import reactor.test.StepVerifier;

import java.util.UUID;

@SpringBootTest
@Import(MyContainersConfiguration.class)
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    ReactiveRedisOperations<String, Customer> reactiveRedisOperations;

    @DisplayName("Given a customer, It should be persisted in Redis")
    @Test
    public void shouldRetrieveFromCache() {
        Customer customer = new Customer(UUID.randomUUID().toString(), "John", "Smith");

        StepVerifier.create(customerRepository.save(customer))
        .expectNextMatches(customerCreated -> customerCreated.name().equals(customer.name()))
        .verifyComplete();

        StepVerifier.create(reactiveRedisOperations.opsForValue().get(customer.uuid()).map(__ -> customer))
                .expectNextMatches(customer1 ->
                        (customer1.firstName().equals(customer.firstName()))
                        && (customer1.name().equals(customer.name()))
                        && (customer1.uuid().equals(customer.uuid()))
                ).verifyComplete();
    }
}