package com.learning.student.validationservice.controller.api;

import com.learning.student.validationservice.controller.model.StudentDto;
import com.learning.student.validationservice.controller.model.ValidationDetailsDto;
import com.learning.student.validationservice.persistance.model.Student;
import com.learning.student.validationservice.persistance.model.ValidationDetail;
import com.learning.student.validationservice.service.ValidationService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/student")
public class ValidationController {

    private final ValidationService validationService;
    private ModelMapper modelMapper = new ModelMapper();

    public ValidationController(ValidationService validationService) {
        this.validationService = validationService;
    }

    @GetMapping("/validation/{studentId}")
    public ResponseEntity<List<ValidationDetailsDto>> getValidationDetails(@PathVariable String studentId) {
        List<ValidationDetail> validationDetails = validationService.getValidationForStudent(studentId);
        List<ValidationDetailsDto> response = validationDetails.stream()
                .map(vd -> modelMapper.map(vd, ValidationDetailsDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @GetMapping("/validate/{studentId}")
//    public ValidationDetailsDto validateStudent(@RequestBody StudentDto studentDto, @PathVariable String studentId) {
//        ValidationDetails validationDetails = validationService.validate(studentId, modelMapper.map(studentDto, Student.class));
//        return modelMapper.map(validationDetails, ValidationDetailsDto.class);
//    }

    @GetMapping("/validate/{studentId}")
    public ResponseEntity<List<ValidationDetailsDto>> validateStudent(@RequestBody StudentDto studentDto, @PathVariable String studentId) {
        List<ValidationDetail> validationDetails = validationService.validateStudent(studentId, modelMapper.map(studentDto, Student.class));
        List<ValidationDetailsDto> response = validationDetails.stream()
                .map(vd -> modelMapper.map(vd, ValidationDetailsDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
