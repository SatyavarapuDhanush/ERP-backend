package com.erp.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.erp.backend.dto.CourseDto;
import com.erp.backend.model.CoursesModel;

@Service
public interface CoursesService {
     public CoursesModel addCourse(CoursesModel newCourse);
     public List<CoursesModel> getAllCourses();
     public CoursesModel getCourseById(Long courseId);
     public void deleteCourse(Long courseId);
     //public CoursesModel updateCourse(Long courseId,CoursesModel updatedCourse); 
     public boolean updateCourse(Long id, CourseDto courseDto);
}
