package com.learning.student.validationservice.service;

import com.learning.student.validationservice.persistance.model.Student;
import com.learning.student.validationservice.persistance.model.ValidationDetail;
import com.learning.student.validationservice.persistance.repository.ValidationRepository;
import com.learning.student.validationservice.service.impl.ValidationServiceImpl;
import com.learning.student.validationservice.service.util.CustomMessages;
import com.learning.student.validationservice.service.util.ValidationCalculator;
import com.learning.student.validationservice.util.ValidationTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ValidationServiceTest {
    ValidationRepository validationRepository;
    ValidationCalculator validationCalculator;
    ValidationService validationService;

    ValidationDetail validationDetail = ValidationTestData.getValidationDetails();
    Student student = ValidationTestData.getStudent();
    CustomMessages customMessages = ValidationTestData.getCustomMessages();

    @BeforeEach
    void setUp() {
        validationRepository = mock(ValidationRepository.class);
        validationCalculator = mock(ValidationCalculator.class);
        validationService = new ValidationServiceImpl(validationRepository, validationCalculator);
    }

    @Test
    void getValidationForStudentReturnsValidList() {
        // Given
        List<ValidationDetail> expectedList = Collections.singletonList(validationDetail);
        when(validationRepository.findByStudentId(ValidationTestData.STUDENT_UUID))
                .thenReturn(expectedList);

        // When
        List<ValidationDetail> actualList = validationService.getValidationForStudent(ValidationTestData.STUDENT_ID);

        // Then
        assertEquals(expectedList.size(), actualList.size());
        assertEquals(expectedList.get(0).getStudentId(), actualList.get(0).getStudentId());
        assertEquals(expectedList.get(0).getErrorName(), actualList.get(0).getErrorName());
    }

    @Test
    void getValidationForStudentReturnsEmptyList() {
        // Given
        when(validationRepository.findByStudentId(ValidationTestData.STUDENT_UUID))
                .thenReturn(Collections.emptyList());

        // When
        List<ValidationDetail> actualList = validationService.getValidationForStudent(ValidationTestData.STUDENT_ID);

        // Then
        assertEquals(0, actualList.size());
    }

    @Test
    void validateStudentReturnsValidList() {
        // Given
        List<ValidationDetail> expectedList = Collections.singletonList(validationDetail);
        when(validationCalculator.triggerRules(student)).thenReturn(customMessages);

        // When
        List<ValidationDetail> actualList = validationService.validateStudent(ValidationTestData.STUDENT_UUID, student);

        // Then
        ArgumentCaptor<ValidationDetail> captor = ArgumentCaptor.forClass(ValidationDetail.class);
        verify(validationRepository).save(captor.capture());
        assertEquals(validationDetail.getErrorName(), captor.getValue().getErrorName());

        assertEquals(expectedList.size(), actualList.size());
        assertEquals(expectedList.get(0).getStudentId(), actualList.get(0).getStudentId());
        assertEquals(expectedList.get(0).getErrorName(), actualList.get(0).getErrorName());
    }

    @Test
    void validateStudentReturnsEmptyList() {
        // Given
        when(validationCalculator.triggerRules(student)).thenReturn(new CustomMessages());

        // When
        List<ValidationDetail> actualList = validationService.validateStudent(ValidationTestData.STUDENT_UUID, student);

        // Then
        verify(validationRepository, never()).save(any(ValidationDetail.class));
        assertEquals(0, actualList.size());
    }
}
