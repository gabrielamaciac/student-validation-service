package com.learning.student.validationservice.service;

import com.learning.student.validationservice.integration.model.ValidationResponse;
import com.learning.student.validationservice.integration.queue.StudentServiceSender;
import com.learning.student.validationservice.persistance.model.Student;
import com.learning.student.validationservice.persistance.model.ValidationDetail;
import com.learning.student.validationservice.service.impl.SendValidationServiceImpl;
import com.learning.student.validationservice.service.impl.ValidationServiceImpl;
import com.learning.student.validationservice.util.ValidationTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SendValidationServiceImplTest {
    StudentServiceSender studentServiceSender;
    ValidationService validationService;
    SendValidationService sendValidationService;

    Student student = ValidationTestData.getStudent();
    ValidationDetail validationDetail = ValidationTestData.getValidationDetails();

    @BeforeEach
    void setUp() {
        studentServiceSender = mock(StudentServiceSender.class);
        validationService = mock(ValidationServiceImpl.class);
        sendValidationService = new SendValidationServiceImpl(studentServiceSender, validationService);
    }

    @Test
    void validateAndSendIsSuccessfulForInvalidStudent() {
        // Given
        List<ValidationDetail> expectedList = Collections.singletonList(validationDetail);
        when(validationService.validateStudent(ValidationTestData.STUDENT_UUID, student)).thenReturn(expectedList);

        // When
        sendValidationService.validateAndSend(ValidationTestData.STUDENT_UUID, student);

        // Then
        ArgumentCaptor<ValidationResponse> captor = ArgumentCaptor.forClass(ValidationResponse.class);
        verify(studentServiceSender).sendValidation(captor.capture());

        ValidationResponse validationResponse = captor.getValue();
        assertFalse(validationResponse.isValid());
        assertEquals(ValidationTestData.STUDENT_UUID, validationResponse.getStudentId());
    }

    @Test
    void validateAndSendIsSuccessfulForValidStudent() {
        // Given
        when(validationService.validateStudent(ValidationTestData.STUDENT_UUID, student)).thenReturn(Collections.emptyList());

        // When
        sendValidationService.validateAndSend(ValidationTestData.STUDENT_UUID, student);

        // Then
        ArgumentCaptor<ValidationResponse> captor = ArgumentCaptor.forClass(ValidationResponse.class);
        verify(studentServiceSender).sendValidation(captor.capture());

        ValidationResponse validationResponse = captor.getValue();
        assertTrue(validationResponse.isValid());
        assertEquals(ValidationTestData.STUDENT_UUID, validationResponse.getStudentId());
    }
}
