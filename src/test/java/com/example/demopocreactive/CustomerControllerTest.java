package com.example.demopocreactive;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@ExtendWith(SpringExtension.class)
@WebFluxTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private WebTestClient webClient;

    @Test
    void create() throws JsonProcessingException {

        CustomerDTO customerDTO = new CustomerDTO("John", "Smith");
        String json = new ObjectMapper().writeValueAsString(customerDTO);

        webClient
                .post()
                .uri("/customer")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(json)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.uuid").isNotEmpty()
                .jsonPath("$.firstName").isEqualTo(customerDTO.firstName())
                .jsonPath("$.name").isEqualTo(customerDTO.name());

    }
}