package com.learning.student.validationservice.service.impl;

import com.learning.student.validationservice.integration.model.ValidationResponse;
import com.learning.student.validationservice.integration.queue.StudentServiceSender;
import com.learning.student.validationservice.persistance.model.Student;
import com.learning.student.validationservice.persistance.model.ValidationDetail;
import com.learning.student.validationservice.persistance.repository.ValidationRepository;
import com.learning.student.validationservice.service.ValidationService;
import com.learning.student.validationservice.service.util.CustomMessage;
import com.learning.student.validationservice.service.util.CustomMessages;
import com.learning.student.validationservice.service.util.ValidationCalculator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ValidationServiceImpl implements ValidationService {
    @Autowired
    private ValidationRepository validationRepository;

    @Autowired
    private ValidationCalculator validationCalculator;

    @Autowired
    private StudentServiceSender studentServiceSender;

    @Override
    public List<ValidationDetail> getValidationForStudent(String id) {
        return validationRepository.findByStudentId(UUID.fromString(id));
    }

    @Override
    public List<ValidationDetail> validateStudent(String studentId, Student student) {
        List<ValidationDetail> validationDetails = new ArrayList<>();
        //trigger rules
        CustomMessages result = validationCalculator.triggerRules(student);

        //save each validation error raised on the given student
        for (CustomMessage message : result.getMessages()) {
            ValidationDetail validationDetail = new ValidationDetail();
            validationDetail.setErrorName(message.getMessage());
            validationDetail.setStudentId(UUID.fromString(studentId));
            validationDetail.setErrorDescription(message.getDescription());
            validationDetails.add(validationDetail);
            validationRepository.save(validationDetail);
        }

        //send the flag and the studentId to student-service
        boolean flag = false;
        if (validationDetails.size() == 0) {
            flag = true;
        }
        ValidationResponse validationResponse = new ValidationResponse();
        validationResponse.setStudentId(studentId);
        validationResponse.setValid(flag);
        studentServiceSender.sendValidation(validationResponse);
        log.info("Validation sent to student-service. isValid: " + flag);
        return validationDetails;
    }
}
