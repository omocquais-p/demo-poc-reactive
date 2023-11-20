package com.example.demopocreactive;

import io.micrometer.observation.ObservationRegistry;
import org.springframework.stereotype.Service;
import reactor.core.observability.micrometer.Micrometer;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final ObservationRegistry registry;

    public CustomerService(CustomerRepository customerRepository, ObservationRegistry registry) {
        this.customerRepository = customerRepository;
        this.registry = registry;
    }

    public Mono<CustomerResponseDTO> create(CustomerDTO customerDTO) {
        return customerRepository
                .save(new Customer(UUID.randomUUID().toString(), customerDTO.firstName(), customerDTO.name()))
                .map(customer -> new CustomerResponseDTO(UUID.fromString(customer.uuid()), customer.name(), customer.firstName()))
                .name("create.customer")
                .tag("customer.name.length", customerDTO.name().length() > 5 ? "long" : "short")
                .tap(Micrometer.observation(registry));
    }

    public Mono<CustomerResponseDTO> get(String uuid) {
        return customerRepository
                .findById(uuid)
                .name("get.customer")
                .tag("uuid", uuid)
                .tap(Micrometer.observation(registry))
                .map(customer -> new CustomerResponseDTO(UUID.fromString(customer.uuid()), customer.name(), customer.firstName()));
    }
}