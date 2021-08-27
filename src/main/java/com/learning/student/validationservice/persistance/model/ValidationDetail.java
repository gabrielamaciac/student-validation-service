package com.learning.student.validationservice.persistance.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "validation")
@NoArgsConstructor
@Data
public class ValidationDetail {
    @Id
    @GeneratedValue
    private UUID id;
    @Column(name = "student_id")
    private UUID studentId;
    @Column(name = "name")
    private String errorName;
    @Column(name = "description")
    private String errorDescription;
}
