package org.example.service;

import org.example.controller.AssignmentRequest;
import org.example.controller.AssignmentResponse;
import org.example.entity.Assignment;

import java.util.List;

public interface AssignmentService {

    AssignmentResponse createAssignment(
            AssignmentRequest assignmentRequest,
            String username
    );
    List<AssignmentResponse> getAllAssignments();
}
