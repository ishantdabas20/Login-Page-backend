package org.example.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class SubmissionResponse {

    private Long submissionId;
    private String fileName;
    private String fileUrl;
    private LocalDateTime submittedAt;
}