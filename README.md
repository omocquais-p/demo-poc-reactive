# Reactive Spring Boot Application with observability

- Build the container image from source code with cloud native buildpacks
```
make build-image
```
 
- Start the application with docker compose
```
make start-app
```

- Call the application to create customers in Redis
```
make start-app
```

## Metrics in Prometheus / Grafana

- create_customer_milliseconds_count
- get_customer_milliseconds_sum