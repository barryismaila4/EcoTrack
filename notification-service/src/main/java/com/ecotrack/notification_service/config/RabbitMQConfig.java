package com.ecotrack.notification_service.config;


import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue wateringQueue() {
        return new Queue("watering.notifications", true);
    }

    @Bean
    public Queue generalQueue() {
        return new Queue("general.notifications", true);
    }
}