package com.learning.student.validationservice.service;

import com.learning.student.validationservice.persistance.model.Student;
import com.learning.student.validationservice.persistance.model.ValidationDetail;

import java.util.List;

public interface ValidationService {
    List<ValidationDetail> getValidationForStudent(String id);

//    ValidationDetail validate(String id, Student student);

    List<ValidationDetail> validateStudent(String studentId, Student student);
}
