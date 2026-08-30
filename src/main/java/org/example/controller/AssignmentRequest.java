package org.example.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;

@Getter
@Setter
public class AssignmentRequest {

    private String title;
    private String subject;
    private String description;
    private Date dueDate;
    private MultipartFile file;

}