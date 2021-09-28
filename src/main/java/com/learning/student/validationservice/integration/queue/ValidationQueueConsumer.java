package com.learning.student.validationservice.integration.queue;

import com.learning.student.validationservice.persistance.model.Student;
import com.learning.student.validationservice.service.SendValidationService;
import com.learning.student.validationservice.util.GenericMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Consumes the message from student-service via validation-queue and validates the Student objects.
 */
@Component
@Slf4j
public class ValidationQueueConsumer {

    private final SendValidationService sendValidationService;

    public ValidationQueueConsumer(SendValidationService sendValidationService) {
        this.sendValidationService = sendValidationService;
    }

    @RabbitListener(queues = "validation-queue")
    public void receiveMessage(String message) {
        processMessage(message);
    }

    private void processMessage(String message) {
        log.info("Validating message: " + message);
        Student student = GenericMapper.readValue(message, Student.class);
        sendValidationService.validateAndSend(student.getId(), student);
    }
}
