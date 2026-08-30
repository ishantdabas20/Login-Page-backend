package org.example.service;

import org.example.controller.StudentAssignmentResponse;
import org.example.controller.StudentSubmissionStatusResponse;
import org.example.controller.SubmissionResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SubmissionService {

    // Student view: every assignment + this student's own submission status
    List<StudentAssignmentResponse> getAssignmentsForStudent(String username);

    // Student action: upload/replace their submission for one assignment
    SubmissionResponse submitAssignment(Long assignmentId, MultipartFile file, String username);

    // Faculty/Admin view: every student + whether they've completed a given assignment
    List<StudentSubmissionStatusResponse> getSubmissionStatusForAssignment(Long assignmentId);
}