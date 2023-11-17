package com.example.demopocreactive;

import com.example.demopocreactive.configuration.MyContainersConfiguration;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

//@ExtendWith(SpringExtension.class)
//@WebFluxTest(CustomerController.class)
@SpringBootTest
@Import(MyContainersConfiguration.class)
class CustomerControllerTest {

//    @Autowired
//    private WebTestClient webClient;

    @MockBean
    CustomerService customerService;

    @Test
    @DisplayName("Given a customer (firstName and name), the customer service is called to create the customer in Redis")
    void create() throws JsonProcessingException {

        CustomerDTO customerDTO = new CustomerDTO("John", "Smith");
        UUID uuid = UUID.randomUUID();

        when(customerService.create(customerDTO)).thenReturn(Mono.just(new CustomerResponseDTO(uuid, "John", "Smith")));

        WebTestClient webClient = WebTestClient.bindToController(new CustomerController(customerService))
                .build();

        webClient
                .post()
                .uri("/customer")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ObjectMapper().writeValueAsString(customerDTO))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(CustomerResponseDTO.class).value(customerResponseDTO -> {
                    assertThat(customerResponseDTO.uuid()).isNotNull().isEqualTo(uuid);
                    assertThat(customerResponseDTO.firstName()).isNotNull().isEqualTo(customerDTO.firstName());
                    assertThat(customerResponseDTO.name()).isNotNull().isEqualTo(customerDTO.name());
                });

        verify(customerService).create(customerDTO);
    }

    @Test
    @DisplayName("Given a customer identified by an UUID saved in Redis, it should retrieve the customer from Redis with the UUID given as parameter")
    void get(){

        UUID uuid = UUID.randomUUID();

        CustomerResponseDTO customer = new CustomerResponseDTO(uuid, "John", "Smith");
        when(customerService.get(uuid.toString())).thenReturn(Mono.just(customer));

        WebTestClient webClient = WebTestClient.bindToController(new CustomerController(customerService))
                .build();

        webClient
                .get()
                .uri("/customer/" + uuid)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(CustomerResponseDTO.class).value(customerResponseDTO -> {
                    assertThat(customerResponseDTO.uuid()).isNotNull().isEqualTo(uuid);
                    assertThat(customerResponseDTO.firstName()).isNotNull().isEqualTo(customer.firstName());
                    assertThat(customerResponseDTO.name()).isNotNull().isEqualTo(customer.name());
                });

        verify(customerService).get(uuid.toString());

    }
}