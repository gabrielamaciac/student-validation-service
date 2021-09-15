package com.learning.student.validationservice.integration.queue;

import com.learning.student.validationservice.util.ValidationTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class StudentServiceSenderTest {

    AmqpTemplate amqpTemplate;
    StudentServiceSender studentServiceSender;

    @BeforeEach
    void setUp() {
        amqpTemplate = mock(AmqpTemplate.class);
        studentServiceSender = new StudentServiceSender(amqpTemplate);
    }

    @Test
    void validationResponseCanBeSent() {
        studentServiceSender.sendValidation(ValidationTestData.getValidationResponse());
        verify(amqpTemplate).convertAndSend(any(), any(), any(String.class));
    }
}
