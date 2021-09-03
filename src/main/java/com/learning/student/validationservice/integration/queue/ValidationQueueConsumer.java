package com.learning.student.validationservice.integration.queue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.student.validationservice.persistance.model.Student;
import com.learning.student.validationservice.service.SendValidationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Consumes the message from student-service and validates the Student objects.
 */
@Component
@Slf4j
public class ValidationQueueConsumer {

    @Autowired
    SendValidationService sendValidationService;

    ObjectMapper objectMapper = new ObjectMapper();

    public ValidationQueueConsumer(SendValidationService sendValidationService) {
        this.sendValidationService = sendValidationService;
    }

    @RabbitListener(queues = "validation-queue")
    public void receiveMessage(String message) {
        processMessage(message);
    }

    private void processMessage(String message) {
        try {
            log.info("Validating message: " + message);
            Student student = objectMapper.readValue(message, Student.class);
            sendValidationService.validateAndSend(student.getId(), student);
        } catch (JsonProcessingException e) {
            log.error("Error processing received json: " + e);
        }
    }
}
