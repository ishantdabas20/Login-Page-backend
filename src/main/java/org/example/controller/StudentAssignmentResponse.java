package org.example.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Date;

@Getter
@AllArgsConstructor
public class StudentAssignmentResponse {

    private Long id;
    private String title;
    private String subject;
    private String description;
    private String fileName;
    private String fileUrl;
    private Date dueDate;
    private String createdByUsername;
    private boolean submitted;
    private SubmissionResponse submission; // null when the student hasn't submitted yet
}