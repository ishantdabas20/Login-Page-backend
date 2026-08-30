package org.example.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;
import java.sql.Date;

@Getter
@AllArgsConstructor
public class AssignmentResponse {

    private Long id;
    private String title;
    private String subject;
    private String description;
    private String fileName;
    private String fileUrl;
    private Date dueDate;
    private Long createdBy;
    private String createdByUsername;
    private LocalDateTime createdDate;
}