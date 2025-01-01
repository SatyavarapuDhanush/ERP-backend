package com.erp.backend.serviceimpl;


import java.util.List;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.backend.dto.CourseDto;
import com.erp.backend.exception.CourseNotFoundException;
import com.erp.backend.model.CoursesModel;
import com.erp.backend.repository.CoursesRepository;
import com.erp.backend.service.CoursesService;

@Service
public class CoursesServiceimpl implements CoursesService{

    @Autowired
    public CoursesRepository CJR;
    @Override
    public CoursesModel addCourse(CoursesModel newCourse) {
        CoursesModel course=CJR.save(newCourse);
        return course;
    }
    @Override
    public List<CoursesModel> getAllCourses() {
        return CJR.findAll();
    }
    @Override
    public CoursesModel getCourseById(Long courseId) {
        CoursesModel course=CJR.findByCourseId(courseId).orElseThrow(()->new CourseNotFoundException("Course not found with Id:"+ courseId));
        return course;
    }
    @Override
    public void deleteCourse(Long courseId) {
        CoursesModel course=CJR.findByCourseId(courseId).orElseThrow(()->new CourseNotFoundException("Course not found with Id:"+ courseId));
         CJR.delete(course);
    }
    //@Override
    // public CoursesModel updateCourse(Long courseId, CoursesModel updatedCourse) {
    //     CoursesModel existingcourse=CJR.findByCourseId(courseId).orElseThrow(()->new CourseNotFoundException("Course not found with Id:"+ courseId));
    //     if(updatedCourse.getCoursecode()!=null){
    //     existingcourse.setCoursecode(updatedCourse.getCoursecode());
    //     }
    //     if(updatedCourse.getBranch()!=null){
    //     existingcourse.setBranch(updatedCourse.getBranch());
    //     }
    //     if(updatedCourse.getName()!=null){
    //     existingcourse.setName(updatedCourse.getName());
    //     }
    //     if(updatedCourse.getImage()!=null){
    //     existingcourse.setImage(updatedCourse.getImage());
    //     }
    //     return CJR.save(existingcourse);
    // }

    @Override
    public boolean updateCourse(Long courseId, CourseDto courseDto) {
        // Fetch the course by ID
        Optional<CoursesModel> courseOptional = CJR.findByCourseId(courseId);

        // Update if course exists
        if (courseOptional.isPresent()) {
            CoursesModel course = courseOptional.get();

            // Update fields only if they are not null
            if (courseDto.getCoursecode() != null) {
                course.setCoursecode(courseDto.getCoursecode());
            }
            if (courseDto.getName() != null) {
                course.setName(courseDto.getName());
            }
            if (courseDto.getBranch() != null) {
                course.setBranch(courseDto.getBranch());
            }
            if (courseDto.getImage() != null) {
                course.setImage(courseDto.getImage());
            }

            // Save the updated course back to the repository
            CJR.save(course);
            return true;
        }

        // Return false if the course does not exist
        return false;
    }
}
