package com.erp.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.erp.backend.model.TeacherModel;

public interface TeacherRepository extends JpaRepository<TeacherModel,Long>{

    Optional<TeacherModel> findByTeacherId(Long teacherId);
    
}
