package com.example.demopocreactive;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Test
    void create() {
        CustomerDTO customerDTO = new CustomerDTO("John", "Smith");
        Mono<CustomerResponseDTO> customerMono = customerService.create(customerDTO);
        StepVerifier
                .create(customerMono)
                .consumeNextWith(newCustomer -> {
                    assertEquals(newCustomer.firstName(), customerDTO.firstName());
                    assertEquals(newCustomer.name(), customerDTO.name());
                })
                .verifyComplete();
    }
}