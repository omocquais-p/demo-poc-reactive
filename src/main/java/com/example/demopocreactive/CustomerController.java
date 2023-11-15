package com.example.demopocreactive;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
public class CustomerController {

    @PostMapping("customer")
    public Mono<CustomerResponseDTO> create(@RequestBody Mono<CustomerDTO> customer) {
        return customer.map(
                customerDTO -> new CustomerResponseDTO(UUID.randomUUID(), customerDTO.name(), customerDTO.firstName()));
    }

}
