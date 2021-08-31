package com.learning.student.validationservice.controller.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ValidationDetailDto {
    private UUID studentId;
    private String errorName;
    private String errorDescription;
}
