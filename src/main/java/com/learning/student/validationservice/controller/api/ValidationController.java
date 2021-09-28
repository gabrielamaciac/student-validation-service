package com.learning.student.validationservice.controller.api;

import com.learning.student.validationservice.controller.model.StudentDto;
import com.learning.student.validationservice.controller.model.ValidationDetailDto;
import com.learning.student.validationservice.facade.ValidationFacade;
import com.learning.student.validationservice.persistance.model.Student;
import com.learning.student.validationservice.persistance.model.ValidationDetail;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ValidationController implements ValidationApi {

    private final ValidationFacade validationFacade;
    private final ModelMapper modelMapper;

    public ValidationController(ValidationFacade validationFacade, ModelMapper modelMapper) {
        this.validationFacade = validationFacade;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<List<ValidationDetailDto>> getValidationByStudentId(String studentId) {
        List<ValidationDetail> validationDetails = validationFacade.getValidationForStudent(studentId);
        List<ValidationDetailDto> response = validationDetails.stream()
                .map(vd -> modelMapper.map(vd, ValidationDetailDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<ValidationDetailDto>> validateStudentById(StudentDto studentDto, String studentId) {
        List<ValidationDetail> validationDetails = validationFacade.validateStudent(studentId, modelMapper.map(studentDto, Student.class));
        List<ValidationDetailDto> response = validationDetails.stream()
                .map(vd -> modelMapper.map(vd, ValidationDetailDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
