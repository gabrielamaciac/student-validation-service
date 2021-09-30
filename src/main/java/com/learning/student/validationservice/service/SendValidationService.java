package com.learning.student.validationservice.service;

import com.learning.student.validationservice.persistance.model.Student;

import java.util.UUID;

public interface SendValidationService {
    void validateAndSend(UUID studentId, Student student);
}
