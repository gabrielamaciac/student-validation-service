package com.learning.student.validationservice.controller.api;

import com.learning.student.validationservice.controller.model.StudentDto;
import com.learning.student.validationservice.controller.model.ValidationDetailDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ValidationApi {
    @GetMapping("/student/{studentId}")
    ResponseEntity<List<ValidationDetailDto>> getValidationForStudentById(@PathVariable String studentId);

    @PostMapping("/student/{studentId}")
    ResponseEntity<List<ValidationDetailDto>> validateStudentById(@RequestBody StudentDto studentDto, @PathVariable String studentId);
}
