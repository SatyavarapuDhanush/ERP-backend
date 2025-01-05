package com.erp.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.erp.backend.model.AssignmentModel;

public interface AssignmentRepository extends JpaRepository<AssignmentModel,Long>{

    Optional<AssignmentModel> findByAssId(Long assId);
    
}
