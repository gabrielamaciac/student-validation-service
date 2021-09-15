package com.learning.student.validationservice.service.util;

import com.learning.student.validationservice.persistance.model.Grade;
import com.learning.student.validationservice.persistance.model.Student;
import com.learning.student.validationservice.util.ValidationTestData;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidationCalculatorTest {

    private ValidationCalculator validationCalculator;

    private Student validStudent = ValidationTestData.getStudent();
    private Student invalidStudent = ValidationTestData.getInvalidStudent();

    @Test
    void isAverageBelowFiveReturnsTrue() {
        // Given
        List<Grade> gradeList = ValidationTestData.getGrades(4.0, 3.0);

        // When
        boolean result = ValidationCalculator.isAverageBelowFive(gradeList);

        // Then
        assertTrue(result);
    }

    @Test
    void isAverageBelowFiveReturnsFalse() {
        // Given
        List<Grade> gradeList = ValidationTestData.getGrades(10.0, 10.0);

        // When
        boolean result = ValidationCalculator.isAverageBelowFive(gradeList);

        // Then
        assertFalse(result);
    }
}
