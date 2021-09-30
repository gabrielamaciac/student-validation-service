package com.learning.student.validationservice.service;

import com.learning.student.validationservice.persistance.model.Student;
import com.learning.student.validationservice.persistance.model.ValidationDetail;

import java.util.List;
import java.util.UUID;

public interface ValidationService {
    List<ValidationDetail> getValidationForStudent(String id);

    List<ValidationDetail> validateStudent(UUID studentId, Student student);
}
