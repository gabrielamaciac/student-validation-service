package com.learning.student.validationservice.persistance.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "validation")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
