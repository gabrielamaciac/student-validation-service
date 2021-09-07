package com.learning.student.validationservice.controller.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ValidationDetailDto {
    private String studentId;
    private String errorName;
    private String errorDescription;
}
