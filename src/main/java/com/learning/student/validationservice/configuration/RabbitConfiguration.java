package com.learning.student.validationservice.configuration;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class RabbitConfiguration {
    @Value("${spring.rabbitmq.exchange}")
    private String exchange;
    @Value("${spring.rabbitmq.validationqueue}")
    private String validationQueue;
    @Value("${spring.rabbitmq.validationrouting}")
    private String validationRoutingKey;
    @Value("${spring.rabbitmq.responsequeue}")
    private String responseQueue;
    @Value("${spring.rabbitmq.responserouting}")
    private String responseRoutingKey;

    @Bean
    Queue validationQueue() {
        return new Queue(validationQueue, false);
    }

    @Bean
    Queue responseQueue() {
        return new Queue(responseQueue, false);
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    public Binding validationBinding(Queue validationQueue, DirectExchange exchange) {
        return BindingBuilder.bind(validationQueue).to(exchange).with(validationRoutingKey);
    }

    @Bean
    public Binding responseBinding(Queue validationQueue, DirectExchange exchange) {
        return BindingBuilder.bind(validationQueue).to(exchange).with(responseRoutingKey);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate jsonRabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
