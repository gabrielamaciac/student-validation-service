package com.learning.student.validationservice.facade;

import com.learning.student.validationservice.persistance.model.Student;
import com.learning.student.validationservice.persistance.model.ValidationDetail;
import com.learning.student.validationservice.service.ValidationService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ValidationFacadeImpl implements ValidationFacade {

    private final ValidationService validationService;

    public ValidationFacadeImpl(ValidationService validationService) {
        this.validationService = validationService;
    }

    @Override
    public List<ValidationDetail> getValidationForStudent(String id) {
        return validationService.getValidationForStudent(id);
    }

    @Override
    public List<ValidationDetail> validateStudent(String studentId, Student student) {
        return validationService.validateStudent(studentId, student);
    }
}
