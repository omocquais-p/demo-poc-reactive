package com.example.demopocreactive;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Captor
    ArgumentCaptor<Customer> customerCaptor;

    @Test
    void create() {
        CustomerDTO customerDTO = new CustomerDTO("Smith", "John");
        when(customerRepository.save(any(Customer.class))).thenReturn(Mono.just(new Customer(UUID.randomUUID().toString(), customerDTO.firstName(), customerDTO.name())));

        Mono<CustomerResponseDTO> customerMono = customerService.create(customerDTO);
        StepVerifier
                .create(customerMono)
                .consumeNextWith(newCustomer -> {
                    assertEquals(newCustomer.firstName(), customerDTO.firstName());
                    assertEquals(newCustomer.name(), customerDTO.name());
                })
                .verifyComplete();

        verify(customerRepository).save(customerCaptor.capture());
        Customer customerCaptorValue = customerCaptor.getValue();
        assertThat(customerCaptorValue.name()).isEqualTo(customerDTO.name());
        assertThat(customerCaptorValue.firstName()).isEqualTo(customerDTO.firstName());
    }
}