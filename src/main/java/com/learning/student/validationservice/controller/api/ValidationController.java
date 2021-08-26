package com.learning.student.validationservice.controller.api;

import com.learning.student.validationservice.controller.model.Validation;
import com.learning.student.validationservice.persistance.model.ValidationDetails;
import com.learning.student.validationservice.service.ValidationService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
public class ValidationController {

    private ValidationService validationService;
    private ModelMapper modelMapper = new ModelMapper();

    public ValidationController(ValidationService validationService) {
        this.validationService = validationService;
    }

    @GetMapping("/validate")
    public ResponseEntity<Validation> getValidationDetails(@PathVariable String id) {
        ValidationDetails validationDetails = validationService.getValidationForStudent(id);
        return new ResponseEntity<>(modelMapper.map(validationDetails, Validation.class), HttpStatus.OK);
    }
}
