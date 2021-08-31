package com.learning.student.validationservice.service;

import com.learning.student.validationservice.persistance.model.Student;
import com.learning.student.validationservice.persistance.model.ValidationDetail;

import java.util.List;

public interface ValidationService {
    List<ValidationDetail> getValidationForStudent(String id);

    List<ValidationDetail> validateStudent(String studentId, Student student);
}
