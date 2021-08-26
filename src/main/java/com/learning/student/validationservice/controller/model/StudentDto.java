package com.learning.student.validationservice.controller.model;

import java.util.List;

public class StudentDto {
    private String id;
    private String firstName;
    private String lastName;
    private String cnp;
    private String dateOfBirth;
    private AddressDto address;
    private List<GradeDto> grades;
}
