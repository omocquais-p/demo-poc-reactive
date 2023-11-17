package com.example.demopocreactive;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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

    @Captor
    ArgumentCaptor<String> uuidCaptor;

    @Test
    @DisplayName("Given a customer, it should be persisted into Redis")
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

    @Test
    @DisplayName("Given a customer identified by an UUID saved in Redis, it should retrieve the customer from Redis with the UUID given as parameter")
    void get() {

        String uuid = UUID.randomUUID().toString();

        Customer customer = new Customer(uuid, "John", "Smith");

        when(customerRepository.findById(eq(uuid)))
                .thenReturn(Mono.just(new Customer(uuid, customer.firstName(), customer.name())));

        Mono<CustomerResponseDTO> customerMono = customerService.get(customer.uuid());

        StepVerifier
                .create(customerMono)
                .consumeNextWith(customerFound -> {
                    assertEquals(customerFound.uuid().toString(), customer.uuid());
                    assertEquals(customerFound.firstName(), customer.firstName());
                    assertEquals(customerFound.name(), customer.name());
                })
                .verifyComplete();

        verify(customerRepository).findById(uuidCaptor.capture());
        String uuidCaptorValue = uuidCaptor.getValue();
        assertThat(uuidCaptorValue).isEqualTo(uuid);
    }
}