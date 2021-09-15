package com.learning.student.validationservice.facade;

import com.learning.student.validationservice.persistance.model.Student;
import com.learning.student.validationservice.service.ValidationService;
import com.learning.student.validationservice.util.ValidationTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ValidationFacadeTest {
    private ValidationService validationService;
    private ValidationFacade validationFacade;

    private Student student = ValidationTestData.getStudent();

    @BeforeEach
    void setUp() {
        validationService = mock(ValidationService.class);
        validationFacade = new ValidationFacadeImpl(validationService);
    }

    @Test
    void getValidationForStudentIsCalled() {
        // When
        validationFacade.getValidationForStudent(ValidationTestData.STUDENT_ID);

        // Then
        verify(validationService).getValidationForStudent(ValidationTestData.STUDENT_ID);
    }

    @Test
    void validateStudentIsCalled() {
        // When
        validationFacade.validateStudent(ValidationTestData.STUDENT_ID, student);

        // Then
        verify(validationService).validateStudent(ValidationTestData.STUDENT_ID, student);
    }

}
