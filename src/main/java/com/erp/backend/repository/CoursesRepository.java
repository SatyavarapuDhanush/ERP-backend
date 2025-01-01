package com.erp.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.erp.backend.model.CoursesModel;

public interface CoursesRepository extends JpaRepository<CoursesModel,Long>{

    Optional<CoursesModel> findByCourseId(Long courseId);
    
}
