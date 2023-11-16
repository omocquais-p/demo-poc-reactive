package com.example.demopocreactive;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

//@ExtendWith(SpringExtension.class)
//@WebFluxTest(CustomerController.class)
@SpringBootTest
class CustomerControllerTest {

//    @Autowired
//    private WebTestClient webClient;

    @Test
    void create() throws JsonProcessingException {

        CustomerDTO customerDTO = new CustomerDTO("John", "Smith");
        String json = new ObjectMapper().writeValueAsString(customerDTO);

        WebTestClient webClient = WebTestClient.bindToController(new CustomerController())
                .build();

        webClient
                .post()
                .uri("/customer")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(json)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(CustomerResponseDTO.class).value(customerResponseDTO -> {
                    assertThat(customerResponseDTO.uuid()).isNotNull();
                    assertThat(customerResponseDTO.firstName()).isNotNull().isEqualTo(customerDTO.firstName());
                    assertThat(customerResponseDTO.name()).isNotNull().isEqualTo(customerDTO.name());
                });

    }
}