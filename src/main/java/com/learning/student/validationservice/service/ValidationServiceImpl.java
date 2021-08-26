package com.learning.student.validationservice.service;

import com.learning.student.validationservice.persistance.model.ValidationDetails;
import com.learning.student.validationservice.persistance.repository.ValidationRepository;
import org.springframework.stereotype.Service;

@Service
public class ValidationServiceImpl implements ValidationService {
    ValidationRepository validationRepository;

    public ValidationServiceImpl(ValidationRepository validationRepository) {
        this.validationRepository = validationRepository;
    }

    @Override
    public ValidationDetails getValidationForStudent(String id) {
        return validationRepository.findByStudentId(id);
    }
}
