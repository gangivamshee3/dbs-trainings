package com.classpath.ordermicroservice.config;

import com.classpath.ordermicroservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class DBHealthIndicator implements HealthIndicator{

    private final OrderRepository orderRepository;

    @Override
    public Health health() {
        long count = this.orderRepository.count();
        return count > 0  ? Health.up().withDetail("DB", "Service is up").build() : Health.down().withDetail("DB", "Service is down").build();
    }
}
@Component
class KafkaHealthIndicator implements HealthIndicator{

    @Override
    public Health health() {
       return Health.up().withDetail("Kafka", "Service is up").build();
    }
}

@Component
class GatewayHealthIndicator implements HealthIndicator{

    @Override
    public Health health() {
       return Health.up().withDetail("Payment-Gateway", "Service is up").build();
    }
}
