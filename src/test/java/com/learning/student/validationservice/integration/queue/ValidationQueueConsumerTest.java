package com.learning.student.validationservice.integration.queue;

import com.learning.student.validationservice.persistance.model.Student;
import com.learning.student.validationservice.service.SendValidationService;
import com.learning.student.validationservice.util.ValidationTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ValidationQueueConsumerTest {

    SendValidationService sendValidationService;
    ValidationQueueConsumer validationQueueConsumer;

    @BeforeEach
    void setUp() {
        sendValidationService = mock(SendValidationService.class);
        validationQueueConsumer = new ValidationQueueConsumer(sendValidationService);
    }

    @Test
    void messageIsValidatedAndSent() {
        validationQueueConsumer.receiveMessage(ValidationTestData.STUDENT_JSON);
        verify(sendValidationService).validateAndSend(any(String.class), any(Student.class));
    }
}
