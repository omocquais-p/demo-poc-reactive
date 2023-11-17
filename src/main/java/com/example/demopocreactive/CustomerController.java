package com.example.demopocreactive;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("customer")
    public Mono<CustomerResponseDTO> create(@RequestBody CustomerDTO customer) {
        return customerService.create(customer);
    }

    @GetMapping("customer/{uuid}")
    public Mono<CustomerResponseDTO> create(@PathVariable String uuid) {
        return customerService.get(uuid);
    }

}
