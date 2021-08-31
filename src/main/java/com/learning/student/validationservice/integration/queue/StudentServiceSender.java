package com.learning.student.validationservice.integration.queue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.student.validationservice.integration.model.ValidationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Send the validation flag (true/false) to student-service via validation-response-queue.
 */
@Component
@Slf4j
public class StudentServiceSender {

    ObjectMapper objectMapper = new ObjectMapper();

    @Value("${spring.rabbitmq.responserouting}")
    private String routingKey;
    @Value("${spring.rabbitmq.exchange}")
    private String exchange;

    @Autowired
    private AmqpTemplate jsonRabbitTemplate;

    public void sendValidation(ValidationResponse validationResponse) {
        try {
            String json = objectMapper.writeValueAsString(validationResponse);
            jsonRabbitTemplate.convertAndSend(exchange, routingKey, json);
            log.info("ValidationResponse sent to student-service: " + validationResponse);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
