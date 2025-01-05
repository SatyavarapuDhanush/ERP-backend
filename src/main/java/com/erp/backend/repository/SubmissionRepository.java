package com.erp.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.erp.backend.model.SubmissionModel;

public interface SubmissionRepository extends JpaRepository<SubmissionModel,Long>{
    Optional<SubmissionModel> findBySubmissionId(Long submissionId); 
}
