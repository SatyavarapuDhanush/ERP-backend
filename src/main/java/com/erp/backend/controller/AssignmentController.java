package com.erp.backend.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.erp.backend.exception.AssignmentNotFoundException;
import com.erp.backend.exception.SubmissionNotFoundException;
import com.erp.backend.model.AssignmentModel;
import com.erp.backend.model.SubmissionModel;
import com.erp.backend.model.UserModel;
import com.erp.backend.repository.AssignmentRepository;
import com.erp.backend.repository.SubmissionRepository;
import com.erp.backend.service.AssignmentService;
import com.erp.backend.serviceimpl.FileStorageService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin
@RequestMapping("/api/assignments/")
public class AssignmentController {

    @Autowired
    private AssignmentService as;

    @Autowired
    private AssignmentRepository AJR;

    @Autowired
    private SubmissionRepository SJR;

    @Autowired
    private FileStorageService FSS;


    // @Autowired
    // private ObjectMapper objectMapper;

    // @PostMapping(value = "postassignment",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    // public ResponseEntity<?> postAssignment(
    //     @RequestPart("file") MultipartFile file,
    //     @RequestPart("assignment") AssignmentModel assignmentJson
    // ) {
    //     try {
    //         AssignmentModel assignment = objectMapper.readValue(assignmentJson, AssignmentModel.class);
    //         AssignmentModel savedAssignment = as.createAssignment(file, assignment);
    //         return new ResponseEntity<>(savedAssignment, HttpStatus.CREATED);
    //     } catch (IOException e) {
    //         return new ResponseEntity<>("Error while uploading file: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    //     } catch (Exception e) {
    //         return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }

    @PostMapping(value = "postassignment", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
public ResponseEntity<?> postAssignment(
    @RequestPart("file") MultipartFile file,
    @RequestPart("assignment") String assignmentJson // Accept as String
) {
    try {
        // Deserialize JSON string to AssignmentModel
        ObjectMapper objectMapper = new ObjectMapper();
        AssignmentModel assignment = objectMapper.readValue(assignmentJson, AssignmentModel.class);

        // Call the service to handle the creation
        AssignmentModel savedAssignment = as.createAssignment(file, assignment);
        return new ResponseEntity<>(savedAssignment, HttpStatus.CREATED);
    } catch (IOException e) {
        return new ResponseEntity<>("Error while processing request: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    } catch (Exception e) {
        return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


    @GetMapping("get/{assId}/users")
    public ResponseEntity<?> getAssignedUsers(@PathVariable Long assId) {
        try {
            List<UserModel> users = as.getUsersByAssignmentId(assId);
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error fetching assigned users: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("{assId}/submit")
    public ResponseEntity<?> submitPdf(
        @PathVariable Long assId,
        @RequestParam("userId") Long userId,
        @RequestParam("file") MultipartFile file
    ) {
        try {
            SubmissionModel submission = as.submitPdf(assId, userId, file);
            return new ResponseEntity<>(submission, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>("Error while uploading file: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("{submissionId}/marks")
    public ResponseEntity<?> postMarks(
        @PathVariable Long submissionId,
        @RequestParam("marks") Integer marks
    ) {
        try {
            as.postMarks(submissionId, marks);
            return new ResponseEntity<>("Marks are posted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error posting marks: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // @GetMapping("{assId}/view/Assignments")
    // public ResponseEntity<Resource> viewAssignment(@PathVariable Long assId) throws MalformedURLException{
    //     AssignmentModel assignment=AJR.findByAssId(assId).orElseThrow(()->new AssignmentNotFoundException("Assignment not found with Id:"+assId));
    //     String filepath=assignment.getAssdoc();
    //     Resource resource=FSS.loadFile(filepath);
    //     return  ResponseEntity.ok()
    //     .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
    //     .body(resource);
    // }

    @GetMapping("{assId}/view/Assignments")
public ResponseEntity<Resource> viewAssignment(@PathVariable Long assId) throws MalformedURLException {
    AssignmentModel assignment = AJR.findByAssId(assId).orElseThrow(() -> new AssignmentNotFoundException("Assignment not found with Id: " + assId));
    String filepath = assignment.getAssdoc();
    Resource resource;
    try {
        resource = FSS.loadFile(filepath);
        if (!resource.exists() || !resource.isReadable()) {
            throw new FileNotFoundException("File not found or not readable: " + filepath);
        }
    } catch (Exception e) {
        throw new RuntimeException("Failed to load file: " + filepath, e);
    }
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
        .contentType(MediaType.APPLICATION_PDF)
        .body(resource);
}


    @GetMapping("{assId}/download/Assignments")
    public ResponseEntity<Resource> downloadAssignment(@PathVariable Long assId) throws MalformedURLException{
        AssignmentModel assignment=AJR.findByAssId(assId).orElseThrow(()->new AssignmentNotFoundException("Assignment not found with Id:"+assId));
        String filepath=assignment.getAssdoc();
        Resource resource=FSS.loadFile(filepath);
        return  ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
        .body(resource);
    }

    @GetMapping("{submissionId}/view/submission")
    public ResponseEntity<Resource> viewSubmission(@PathVariable Long submissionId) throws MalformedURLException{
         SubmissionModel submission=SJR.findBySubmissionId(submissionId).orElseThrow(()->new SubmissionNotFoundException("Submission not found with Id:"+submissionId));
         String filepath=submission.getPdf();
         Resource resource=FSS.loadFile(filepath);
        return  ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
        .contentType(MediaType.APPLICATION_PDF)
        .body(resource);
    }

    @GetMapping("{submissionId}/download/submission")
    public ResponseEntity<Resource> downloadSubmission(@PathVariable Long submissionId) throws MalformedURLException{
        SubmissionModel submission=SJR.findBySubmissionId(submissionId).orElseThrow(()->new SubmissionNotFoundException("Submission not found with Id:"+submissionId));
        String filepath=submission.getPdf();
        Resource resource=FSS.loadFile(filepath);
       return  ResponseEntity.ok()
       .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
       .body(resource);
   }

    
}
