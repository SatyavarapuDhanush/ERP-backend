package com.erp.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.erp.backend.dto.CourseDto;
import com.erp.backend.model.CoursesModel;
import com.erp.backend.service.CoursesService;



@RestController
@CrossOrigin
@RequestMapping("/api/courses")
public class CoursesController {
    
    @Autowired
    public CoursesService cs;

    @PostMapping("/addcourse")
public ResponseEntity<CoursesModel> addCourse(
    @RequestParam("coursecode") String coursecode,
    @RequestParam("name") String name,
    @RequestParam("branch") String branch,
    @RequestParam("image") MultipartFile image) {
    try {
        CoursesModel course = new CoursesModel();
        course.setCoursecode(coursecode);
        course.setName(name);
        course.setBranch(branch);
        course.setImage(image.getBytes()); // Convert uploaded file to byte array

        CoursesModel savedCourse = cs.addCourse(course);
        return new ResponseEntity<>(savedCourse, HttpStatus.CREATED);
    } catch (Exception e) {
        e.printStackTrace();
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


    // @PostMapping("/addcourse")
    // public ResponseEntity<CoursesModel> addCourse(@ModelAttribute CourseDto newCourseDto) {
    //     try {
    //         CoursesModel course = new CoursesModel();
    //         course.setCoursecode(newCourseDto.getCoursecode());
    //         course.setBranch(newCourseDto.getBranch());
    //         course.setName(newCourseDto.getName());
    //         course.setImage(newCourseDto.getImage()); 
    
    //         CoursesModel savedCourse = cs.addCourse(course);
    //         return new ResponseEntity<>(savedCourse, HttpStatus.CREATED);
    //     } catch (Exception e) {
    //         return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }
    
    @GetMapping("/getcourses")
    public ResponseEntity<List<CourseDto>> getAll(){
        List<CoursesModel> courses = cs.getAllCourses();
        if(courses.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }  
        List<CourseDto> courseDto=courses.stream()
                      .map(course -> new CourseDto(course.getCourseId(),course.getCoursecode(),course.getName(),course.getBranch(),course.getImage()) )
                      .toList();
        return new ResponseEntity<>(courseDto,HttpStatus.OK);
    }

    @GetMapping("/getcourse/{courseId}")
    public ResponseEntity<CoursesModel> getById(@PathVariable("courseId") Long courseId){
        try{
          CoursesModel course=cs.getCourseById(courseId);
          return new ResponseEntity<>(course, HttpStatus.OK);
        }
        catch(Exception e){
             return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("delete/{courseId}")
    public ResponseEntity<String> deleteSpecificCourse(@PathVariable("courseId") Long courseId){
        try{
            cs.deleteCourse(courseId);
            return new ResponseEntity<String>("The course"+ courseId +" deleted successfully ", HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>("An error occured while deleting the course", HttpStatus.NOT_FOUND);
        }
    } 

    // @PutMapping("update/{courseId}")
    // public ResponseEntity<CoursesModel> updateCourseByDetails(@PathVariable("courseId") Long courseId, @RequestBody CourseDto courseDto) {
    //     try{
    //         CoursesModel course=cs.getCourseById(courseId);
    //         if (course == null) {
    //             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    //         }
    //         course.setCoursecode(courseDto.getCoursecode());
    //         course.setBranch(courseDto.getBranch());
    //         course.setName(courseDto.getName());
    //         course.setImage(courseDto.getImage()); 
    //         CoursesModel updatedcourse=cs.updateCourse(courseId, course);
    //         return new ResponseEntity<>(updatedcourse, HttpStatus.OK);
    //     }
    //     catch(Exception e) {
    //     return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    // }
    // }
    

@PostMapping(value = "/update/{courseId}", consumes = { "multipart/form-data" })
public ResponseEntity<String> updateCourse(
    @PathVariable("courseId") Long courseId, 
    @RequestParam("coursecode") String coursecode, 
    @RequestParam("name") String name, 
    @RequestParam("branch") String branch, 
    @RequestParam("image") MultipartFile image) {

    try {
        // Convert image to byte array
        byte[] imageBytes = image.getBytes();

        // Create CourseDto and populate it
        CourseDto courseDto = new CourseDto();
        courseDto.setCourseId(courseId);
        courseDto.setCoursecode(coursecode);
        courseDto.setName(name);
        courseDto.setBranch(branch);
        courseDto.setImage(imageBytes);

        // Call the service method to update the course
        boolean isUpdated = cs.updateCourse(courseId, courseDto);
        
        if (isUpdated) {
            return ResponseEntity.ok("Course updated successfully.");
        } else {
            return ResponseEntity.badRequest().body("Course not found or update failed.");
        }
    } catch (Exception e) {
        return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
    }
}


    
  
}
