package com.learning.student.validationservice.controller.api;

import com.learning.student.validationservice.controller.model.StudentDto;
import com.learning.student.validationservice.controller.model.ValidationDetailDto;
import com.learning.student.validationservice.persistance.model.Student;
import com.learning.student.validationservice.persistance.model.ValidationDetail;
import com.learning.student.validationservice.service.ValidationService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/validation")
public class ValidationController implements ValidationApi {

    private final ValidationService validationService;
    private ModelMapper modelMapper = new ModelMapper();

    public ValidationController(ValidationService validationService) {
        this.validationService = validationService;
    }

    @Override
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<ValidationDetailDto>> getValidationByStudentId(@PathVariable String studentId) {
        List<ValidationDetail> validationDetails = validationService.getValidationForStudent(studentId);
        List<ValidationDetailDto> response = validationDetails.stream()
                .map(vd -> modelMapper.map(vd, ValidationDetailDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @PostMapping("/student/{studentId}")
    public ResponseEntity<List<ValidationDetailDto>> validateStudentById(@RequestBody StudentDto studentDto, @PathVariable String studentId) {
        List<ValidationDetail> validationDetails = validationService.validateStudent(studentId, modelMapper.map(studentDto, Student.class));
        List<ValidationDetailDto> response = validationDetails.stream()
                .map(vd -> modelMapper.map(vd, ValidationDetailDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
