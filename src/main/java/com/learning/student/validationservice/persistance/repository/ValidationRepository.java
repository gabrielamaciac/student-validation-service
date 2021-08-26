package com.learning.student.validationservice.persistance.repository;

import com.learning.student.validationservice.persistance.model.ValidationDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ValidationRepository extends JpaRepository<ValidationDetails, UUID> {
    ValidationDetails findByStudentId(String id);
}
