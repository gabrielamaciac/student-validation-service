package com.learning.student.validationservice.persistance.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Student {
    private UUID id;
    private String firstName;
    private String lastName;
    private String cnp;
    private String dateOfBirth;
    private Address address;
    private List<Grade> grades;
    private boolean isValid;
}
