package com.erp.backend.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.backend.exception.TeacherNotFoundException;
import com.erp.backend.model.TeacherModel;
import com.erp.backend.repository.TeacherRepository;
import com.erp.backend.service.TeacherService;

@Service
public class TeacherServiceimpl implements TeacherService {
  
    @Autowired
    public TeacherRepository TJR;

    @Override
    public TeacherModel createTeacher(TeacherModel newteacher) {
        TeacherModel teacher=TJR.save(newteacher);
        return teacher;
    }

    @Override
    public List<TeacherModel> getAllTeachers() {
        return TJR.findAll();
    }

    @Override
    public TeacherModel getTeacherById(Long teacherId) {
        TeacherModel teacher=TJR.findByTeacherId(teacherId).orElseThrow(()->new RuntimeException("Teacher not found with ID: " + teacherId));
        return teacher;
    }

    @Override
    public void deleteTeacher(Long teacherId) {
        TeacherModel teacher=TJR.findByTeacherId(teacherId).orElseThrow(()->new RuntimeException("Teacher not found with ID: " + teacherId));
        TJR.delete(teacher);
    }

    @Override
    public TeacherModel updateTeacher(Long teacherId, TeacherModel selectedTeacher) {
       TeacherModel teacher=TJR.findByTeacherId(teacherId).orElseThrow(()-> new TeacherNotFoundException("Teacher not found with ID: " + teacherId));
        if (selectedTeacher.getName() != null) {
            teacher.setName(selectedTeacher.getName());
        }
        if (selectedTeacher.getDept() != null) {
            teacher.setDept(selectedTeacher.getDept());
        }
        if (selectedTeacher.getEmail() != null) {
            teacher.setEmail(selectedTeacher.getEmail());
        }
        if (selectedTeacher.getPassword() != null) {
            teacher.setPassword(selectedTeacher.getPassword());
        }
        return TJR.save(teacher);
    }   
    
}
