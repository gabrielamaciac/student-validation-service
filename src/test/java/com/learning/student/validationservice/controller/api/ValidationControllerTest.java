package com.learning.student.validationservice.controller.api;

import com.learning.student.validationservice.controller.model.StudentDto;
import com.learning.student.validationservice.controller.model.ValidationDetailDto;
import com.learning.student.validationservice.facade.ValidationFacade;
import com.learning.student.validationservice.persistance.model.Student;
import com.learning.student.validationservice.persistance.model.ValidationDetail;
import com.learning.student.validationservice.util.ValidationTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test for {@link ValidationController}
 */
class ValidationControllerTest {
    private ValidationFacade validationService;
    private ValidationController validationController;
    private ModelMapper modelMapper;

    private ValidationDetail validationDetail = ValidationTestData.getValidationDetails();
    private Student student = ValidationTestData.getStudent();
    private StudentDto studentDto = ValidationTestData.getStudentDto();


    @BeforeEach
    void setUp() {
        validationService = mock(ValidationFacade.class);
        modelMapper = new ModelMapper();
        validationController = new ValidationController(validationService, modelMapper);
    }

    @Test
    void getValidationByStudentIdReturnsValidList() {
        // Given
        List<ValidationDetail> expectedList = Collections.singletonList(validationDetail);
        when(validationService.getValidationForStudent(ValidationTestData.STUDENT_ID)).thenReturn(expectedList);

        // When
        ResponseEntity<List<ValidationDetailDto>> response = validationController.getValidationByStudentId(ValidationTestData.STUDENT_ID);

        // Then
        List<ValidationDetailDto> actualList = response.getBody();
        assertEquals(expectedList.size(), actualList.size());
        assertEquals(expectedList.get(0).getErrorName(), actualList.get(0).getErrorName());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void validateStudentByIdReturnsValidList() {
        // Given
        List<ValidationDetail> expectedList = Collections.singletonList(validationDetail);
        when(validationService.validateStudent(any(String.class), any(Student.class))).thenReturn(expectedList);

        // When
        ResponseEntity<List<ValidationDetailDto>> response = validationController.validateStudentById(studentDto, ValidationTestData.STUDENT_ID);

        // Then
        List<ValidationDetailDto> actualList = response.getBody();
        assertEquals(expectedList.size(), actualList.size());
        assertEquals(expectedList.get(0).getErrorName(), actualList.get(0).getErrorName());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
