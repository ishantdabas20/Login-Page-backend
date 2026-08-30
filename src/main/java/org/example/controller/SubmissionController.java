package org.example.controller;

import org.example.service.SubmissionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/submissions")
public class SubmissionController {

    private final SubmissionService submissionService;

    public SubmissionController(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    /**
     * Student: list every assignment along with this student's own
     * submission status (submitted or not, and the submitted file if so).
     */
    @GetMapping("/my-assignments")
    public ResponseEntity<List<StudentAssignmentResponse>> getMyAssignments(
            Authentication authentication) {

        String username = authentication.getName();

        return ResponseEntity.ok(
                submissionService.getAssignmentsForStudent(username)
        );
    }

    /**
     * Student: upload (or replace) their submission file for one assignment.
     */
    @PostMapping(value = "/{assignmentId}", consumes = "multipart/form-data")
    public ResponseEntity<SubmissionResponse> submitAssignment(
            @PathVariable Long assignmentId,
            @RequestParam("file") MultipartFile file,
            Authentication authentication) {

        String username = authentication.getName();

        SubmissionResponse response =
                submissionService.submitAssignment(assignmentId, file, username);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * Faculty/Admin: see every student and whether they've completed
     * a given assignment.
     */
    @GetMapping("/assignment/{assignmentId}/status")
    public ResponseEntity<List<StudentSubmissionStatusResponse>> getSubmissionStatus(
            @PathVariable Long assignmentId) {

        return ResponseEntity.ok(
                submissionService.getSubmissionStatusForAssignment(assignmentId)
        );
    }
}