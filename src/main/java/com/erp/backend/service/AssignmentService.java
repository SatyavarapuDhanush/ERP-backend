package com.erp.backend.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.erp.backend.model.AssignmentModel;
import com.erp.backend.model.SubmissionModel;
import com.erp.backend.model.UserModel;

@Service
public interface AssignmentService {

    public AssignmentModel createAssignment(MultipartFile assdoc,AssignmentModel assignment) throws IOException;
    public List<UserModel> getUsersByAssignmentId(Long assId);
    public SubmissionModel submitPdf(Long assId,Long userId,MultipartFile pdfFile)throws IOException;
    public void postMarks(Long submissionId,Integer marks);
} 
