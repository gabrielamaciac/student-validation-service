package com.learning.student.validationservice.service;

import com.learning.student.validationservice.persistance.model.ValidationDetails;

public interface ValidationService {
    ValidationDetails getValidationForStudent(String id);
}
