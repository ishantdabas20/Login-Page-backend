package org.example.controller;

import org.example.service.AssignmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    private final AssignmentService assignmentService;

    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<AssignmentResponse> createAssignment(
            @ModelAttribute AssignmentRequest assignmentRequest,
            Authentication authentication) {

        String username = authentication.getName();

        AssignmentResponse response =
                assignmentService.createAssignment(
                        assignmentRequest,
                        username
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<AssignmentResponse>> getAllAssignments() {

        return ResponseEntity.ok(
                assignmentService.getAllAssignments()
        );
    }
}