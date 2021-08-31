package com.learning.student.validationservice.integration.queue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.student.validationservice.persistance.model.Student;
import com.learning.student.validationservice.persistance.model.ValidationDetail;
import com.learning.student.validationservice.service.ValidationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Consumes the message from student-service and validates the Student objects.
 */
@Component
@Slf4j
public class ValidationQueueConsumer {

    @Autowired
    ValidationService validationService;

    ObjectMapper objectMapper = new ObjectMapper();

    public ValidationQueueConsumer(ValidationService validationService) {
        this.validationService = validationService;
    }

    @RabbitListener(queues = "validation-queue")
    public void receiveMessage(String message) {
        processMessage(message);
    }

    private void processMessage(String message) {
        try {
            log.info("Validating message: " + message);
            Student student = objectMapper.readValue(message, Student.class);
            List<ValidationDetail> validationDetails = validationService.validateStudent(student.getId(), student);
            log.info("ValidationDetails found for student: " + validationDetails.size());
            //todo send back the studentId and boolean isValid true/false - send to new queue
        } catch (JsonProcessingException e) {
            log.error("Error processing received json: " + e);
        }
    }
}
