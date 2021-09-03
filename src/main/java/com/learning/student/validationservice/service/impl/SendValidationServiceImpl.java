package com.learning.student.validationservice.service.impl;

import com.learning.student.validationservice.integration.model.ValidationResponse;
import com.learning.student.validationservice.integration.queue.StudentServiceSender;
import com.learning.student.validationservice.persistance.model.Student;
import com.learning.student.validationservice.persistance.model.ValidationDetail;
import com.learning.student.validationservice.service.SendValidationService;
import com.learning.student.validationservice.service.ValidationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class SendValidationServiceImpl implements SendValidationService {

    StudentServiceSender studentServiceSender;
    ValidationService validationService;

    public SendValidationServiceImpl(StudentServiceSender studentServiceSender, ValidationService validationService) {
        this.studentServiceSender = studentServiceSender;
        this.validationService = validationService;
    }

    @Override
    public void validateAndSend(String studentId, Student student) {
        // call ValidationService and check if student is valid
        List<ValidationDetail> validationDetails = validationService.validateStudent(studentId, student);

        //if the response has no validation details then the flag is true
        boolean flag = validationDetails.isEmpty();

        // construct the validation response with student id and isValid (true/false)
        ValidationResponse validationResponse = new ValidationResponse(studentId, flag);

        //sent this response to student-service via validation-response-queue
        studentServiceSender.sendValidation(validationResponse);

        log.info("Validation sent to student-service. isValid: " + flag);
    }
}
