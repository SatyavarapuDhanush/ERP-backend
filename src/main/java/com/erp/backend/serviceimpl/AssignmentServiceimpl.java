package com.erp.backend.serviceimpl;


import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.erp.backend.exception.AssignmentNotFoundException;
import com.erp.backend.exception.SubmissionNotFoundException;
import com.erp.backend.exception.UserNotFoundException;
import com.erp.backend.model.AssignmentModel;
import com.erp.backend.model.SubmissionModel;
import com.erp.backend.model.UserModel;
import com.erp.backend.repository.AssignmentRepository;
import com.erp.backend.repository.SubmissionRepository;
import com.erp.backend.repository.UserRepository;
import com.erp.backend.service.AssignmentService;

@Service
public class AssignmentServiceimpl implements AssignmentService{

    @Autowired
    private UserRepository UJR;

    @Autowired
    private AssignmentRepository AJR;

    @Autowired
    private SubmissionRepository SJR;

    @Autowired
    private FileStorageService FSS;

    @Override
    public AssignmentModel createAssignment(MultipartFile assdoc, AssignmentModel assignment) throws IOException {
        String docPath = FSS.saveFile(assdoc, "assignments/" + assignment.getAssName());
        assignment.setAssdoc(docPath);
        List<UserModel> users=UJR.findByBranch(assignment.getBranch());
        assignment.setAssignedUsers(users);

        return AJR.save(assignment);
    }

    @Override   
    public List<UserModel> getUsersByAssignmentId(Long assId) {
        AssignmentModel ass=AJR.findByAssId(assId).orElseThrow(()->new AssignmentNotFoundException("Assignment not found with the id:"+assId));
       return ass.getAssignedUsers();
    }

    @Override
    public SubmissionModel submitPdf(Long assId, Long userId, MultipartFile pdfFile) throws IOException {
        AssignmentModel ass=AJR.findByAssId(assId).orElseThrow(()->new AssignmentNotFoundException("Assignment not found with the id"+assId));
        UserModel user=UJR.findByUserId(userId).orElseThrow(()->new UserNotFoundException("User not found with the id:"+userId));
        String pdfpath=FSS.saveFile(pdfFile,"subissions/"+assId+"/"+userId);

        SubmissionModel submission=new SubmissionModel();
        submission.setAssignment(ass);
        submission.setUser(user);
        submission.setPdf(pdfpath);
        return SJR.save(submission);
    }

	@Override
	public void postMarks(Long submissionId, Integer marks) {
		SubmissionModel submission=SJR.findBySubmissionId(submissionId).orElseThrow(()->new SubmissionNotFoundException("Submission not found with the id:"+submissionId));
        submission.setMarks(marks);
        UserModel user=submission.getUser();
        user.setMarks(marks);
        SJR.save(submission);
	}

    

    

}
