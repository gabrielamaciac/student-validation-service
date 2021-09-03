package com.learning.student.validationservice.service;

import com.learning.student.validationservice.persistance.model.Student;

public interface SendValidationService {
    void validateAndSend(String studentId, Student student);
}
