package com.example.demopocreactive.configuration;

import com.redis.testcontainers.RedisContainer;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

public interface MyContainers {
  @Container
  @ServiceConnection
  RedisContainer redisContainer = new RedisContainer(DockerImageName.parse("redis:7.0-alpine"))
          .withExposedPorts(6379);
}
