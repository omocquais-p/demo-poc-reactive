package com.example.demopocreactive;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

//@ExtendWith(SpringExtension.class)
//@WebFluxTest(CustomerController.class)
@SpringBootTest
class CustomerControllerTest {

//    @Autowired
//    private WebTestClient webClient;

    @Autowired
    CustomerService customerService;

    @Test
    void create() throws JsonProcessingException {

        CustomerDTO customerDTO = new CustomerDTO("John", "Smith");
        String json = new ObjectMapper().writeValueAsString(customerDTO);

        WebTestClient webClient = WebTestClient.bindToController(new CustomerController(customerService))
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

//        verify(customerService);

    }
}