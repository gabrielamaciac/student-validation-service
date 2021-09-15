package com.learning.student.validationservice.controller.api;

import com.learning.student.validationservice.controller.model.StudentDto;
import com.learning.student.validationservice.controller.model.ValidationDetailDto;
import com.learning.student.validationservice.facade.ValidationFacade;
import com.learning.student.validationservice.persistance.model.Student;
import com.learning.student.validationservice.persistance.model.ValidationDetail;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Validation Service", description = "The validation service API for student validations.")
@RequestMapping("/validation")
public class ValidationController implements ValidationApi {

    private final ValidationFacade validationFacade;
    private ModelMapper modelMapper = new ModelMapper();

    public ValidationController(ValidationFacade validationFacade) {
        this.validationFacade = validationFacade;
    }

    @Override
    @Operation(summary = "Get validations by student id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Validations found.", content = {
                    @Content(mediaType = "application/json", array =
                    @ArraySchema(schema = @Schema(implementation = ValidationDetailDto.class)))})})
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<ValidationDetailDto>> getValidationByStudentId(@PathVariable String studentId) {
        List<ValidationDetail> validationDetails = validationFacade.getValidationForStudent(studentId);
        List<ValidationDetailDto> response = validationDetails.stream()
                .map(vd -> modelMapper.map(vd, ValidationDetailDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @Operation(summary = "Validate a student by its id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student validated.", content = {
                    @Content(mediaType = "application/json", array =
                    @ArraySchema(schema = @Schema(implementation = ValidationDetailDto.class)))})})
    @PostMapping("/student/{studentId}")
    public ResponseEntity<List<ValidationDetailDto>> validateStudentById(@RequestBody StudentDto studentDto,
                                                                         @PathVariable String studentId) {
        List<ValidationDetail> validationDetails = validationFacade.validateStudent(studentId, modelMapper.map(studentDto, Student.class));
        List<ValidationDetailDto> response = validationDetails.stream()
                .map(vd -> modelMapper.map(vd, ValidationDetailDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
