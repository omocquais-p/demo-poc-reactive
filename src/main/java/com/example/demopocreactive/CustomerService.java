package com.example.demopocreactive;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class CustomerService {

    public Mono<CustomerResponseDTO> create(CustomerDTO customerDTO) {
        return Mono.just(new CustomerResponseDTO(UUID.randomUUID(), customerDTO.name(), customerDTO.firstName()));
    }
}
