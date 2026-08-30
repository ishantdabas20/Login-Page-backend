package org.example.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class StudentSubmissionStatusResponse {

    private Long studentId;
    private String studentUsername;
    private boolean submitted;
    private LocalDateTime submittedAt;
    private String fileName;
    private String fileUrl;
}