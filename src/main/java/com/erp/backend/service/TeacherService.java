package com.erp.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.erp.backend.model.TeacherModel;

@Service
public interface TeacherService {

    public TeacherModel createTeacher(TeacherModel newteacher);
    public List<TeacherModel> getAllTeachers();
    public TeacherModel getTeacherById(Long teacherId);
    public void deleteTeacher(Long teacherId);
    public TeacherModel updateTeacher(Long teacherId,TeacherModel selectedTeacher);
}
