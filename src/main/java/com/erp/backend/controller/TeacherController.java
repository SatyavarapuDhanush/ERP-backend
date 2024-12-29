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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erp.backend.model.TeacherModel;
import com.erp.backend.service.TeacherService;

@RestController
@CrossOrigin
@RequestMapping("/api/auth/teachers")
public class TeacherController {
    
    @Autowired
    public TeacherService ts;

    @PostMapping("/register")
    public ResponseEntity<String> registerTeacher(@RequestBody TeacherModel newTeacher) {
    try {
        TeacherModel teacher = ts.createTeacher(newTeacher);
        String message = String.format("Teacher created successfully. Welcome %s!", teacher.getName());
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    } catch (Exception e) {
        return new ResponseEntity<>("An error occurred while creating the teacher: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }

     @GetMapping("/get")
    public ResponseEntity<List<TeacherModel>> getTeachers() {
    try {
        List<TeacherModel> teachers = ts.getAllTeachers();
        if (teachers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
        }
        return new ResponseEntity<>(teachers, HttpStatus.OK);
    } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }

     @GetMapping("/get/{teacherId}")
    public ResponseEntity<TeacherModel> getById(@PathVariable("teacherId") Long teacherId){
    try{
    TeacherModel teacher=ts.getTeacherById(teacherId);
    return new ResponseEntity<>(teacher, HttpStatus.OK);
    }catch(Exception e){
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }

    @DeleteMapping("/delete/{teacherId}")
    public ResponseEntity<String> deleteUser(@PathVariable("teacherId") Long teacherId){
        try {
            ts.deleteTeacher(teacherId);
            return new ResponseEntity<>("Teacher deleted successfully.", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND); 
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while deleting the Teacher.", HttpStatus.INTERNAL_SERVER_ERROR); 
    }
    }

     @PutMapping("/update/{teacherId}")
    public ResponseEntity<String> updateTeacherByDetails(@PathVariable("teacherId") Long teacherId,@RequestBody TeacherModel existingteacher){
        try{
           TeacherModel updatedteacher=ts.updateTeacher(teacherId, existingteacher);
           String message=String.format("user %s updated successfully! ", updatedteacher.getName());
           return new ResponseEntity<>(message, HttpStatus.OK);
        }catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND); 
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while updating the teacher.", HttpStatus.INTERNAL_SERVER_ERROR); 
    }
    }
}
