package com.example.demopocreactive;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Mono<CustomerResponseDTO> create(CustomerDTO customerDTO) {
        return customerRepository
                .save(new Customer(UUID.randomUUID().toString(), customerDTO.firstName(), customerDTO.name()))
                .map(customer -> new CustomerResponseDTO(UUID.fromString(customer.uuid()), customer.name(), customer.firstName()));
    }
}