package com.learning.student.validationservice.persistance.repository;

import com.learning.student.validationservice.persistance.model.ValidationDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ValidationRepository extends JpaRepository<ValidationDetail, UUID> {
    List<ValidationDetail> findByStudentId(UUID id);
}
