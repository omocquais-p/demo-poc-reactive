package com.example.demopocreactive;

import io.micrometer.observation.ObservationTextPublisher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Hooks;

@SpringBootApplication

public class DemoPocReactiveApplication {

    public static void main(String[] args) {

//        Hooks.enableAutomaticContextPropagation();

        SpringApplication.run(DemoPocReactiveApplication.class, args);
    }

    @Bean
    ObservationTextPublisher otp() {
        return new ObservationTextPublisher();
    }

}
