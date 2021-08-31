package com.learning.student.validationservice.controller.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class StudentDto {
    private String id;
    private String firstName;
    private String lastName;
    private String cnp;
    private String dateOfBirth;
    private AddressDto address;
    private List<GradeDto> grades;
    private boolean isValid;
}
