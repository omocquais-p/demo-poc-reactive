package com.example.demopocreactive;

import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
class CustomerRepository {

    private final ReactiveRedisOperations<String, Customer> reactiveRedisOperations;

    public CustomerRepository(ReactiveRedisOperations<String, Customer> reactiveRedisOperations) {
        this.reactiveRedisOperations = reactiveRedisOperations;
    }

    public Mono<Customer> save(Customer customer) {
        return reactiveRedisOperations.opsForValue()
                .set(customer.uuid(), customer)
                .map(__ -> customer);
    }

}
