package org.example.service;

import org.example.controller.AssignmentRequest;
import org.example.controller.AssignmentResponse;
import org.example.entity.Assignment;
import org.example.entity.User;
import org.example.exception.UserNotFoundException;
import org.example.repository.AssignmentRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AssignmentServiceImpl implements AssignmentService{

    private final AssignmentRepository assignmentRepository;
    private final UserRepository userRepository;

    @Value("${file.upload-dir:uploads/assignments}")
    private String uploadDir;

    public AssignmentServiceImpl(
            AssignmentRepository assignmentRepository,
            UserRepository userRepository) {
        this.assignmentRepository = assignmentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public AssignmentResponse createAssignment(
            AssignmentRequest assignmentRequest,
            String username
    ) {
        User faculty = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UserNotFoundException("Faculty not found")
                );

        if (assignmentRequest.getTitle() == null ||
                assignmentRequest.getTitle().trim().isEmpty()) {

            throw new NullPointerException("Assignment Title is empty");
        }

        if (assignmentRequest.getSubject() == null ||
                assignmentRequest.getSubject().trim().isEmpty()) {

            throw new NullPointerException("Assignment Subject is empty");
        }

        MultipartFile file = assignmentRequest.getFile();

        if (file == null || file.isEmpty()) {
            throw new NullPointerException("Assignment File is empty");
        }
        try {

            Path Uploadpath = Paths.get(uploadDir);

            if (!Files.exists(Uploadpath)) {
                Files.createDirectories(Uploadpath);
            }

            String originalFileName = file.getOriginalFilename();

            if (originalFileName == null || originalFileName.isBlank()) {
                throw new NullPointerException("Invalid File Name");
            }

            String Extensions = "";

            int dotIndex = originalFileName.lastIndexOf('.');

            if (dotIndex >= 0) {
                Extensions = originalFileName.substring(dotIndex);
            }

            String newFileName = UUID.randomUUID() + Extensions;

            Path filePath = Uploadpath.resolve(newFileName);

            Files.copy(
                    file.getInputStream(),
                    filePath,
                    StandardCopyOption.REPLACE_EXISTING
            );

            Assignment assignment = new Assignment();
            assignment.setTitle(assignmentRequest.getTitle().trim());
            assignment.setSubject(assignmentRequest.getSubject().trim());
            assignment.setDescription(assignmentRequest.getDescription());
            assignment.setDueDate(assignmentRequest.getDueDate());
            assignment.setFileName(originalFileName);
            assignment.setFileUrl("/uploads/assignments/" + newFileName);
            assignment.setCreatedBy(faculty);

            Assignment saved = assignmentRepository.save(assignment);

            return new AssignmentResponse(
                    saved.getId(),
                    saved.getTitle(),
                    saved.getSubject(),
                    saved.getDescription(),
                    saved.getFileName(),
                    saved.getFileUrl(),
                    saved.getDueDate(),
                    saved.getCreatedBy().getId(),
                    saved.getCreatedBy().getUsername(),
                    saved.getCreatedAt()
            );

        } catch (IOException e) {

            throw new RuntimeException(
                    "Failed to upload assignment",
                    e
            );
        }
    }

    @Override
    public List<AssignmentResponse> getAllAssignments() {

        return assignmentRepository.findAll()
                .stream()
                .map(a -> new AssignmentResponse(
                        a.getId(),
                        a.getTitle(),
                        a.getSubject(),
                        a.getDescription(),
                        a.getFileName(),
                        a.getFileUrl(),
                        a.getDueDate(),
                        a.getCreatedBy().getId(),
                        a.getCreatedBy().getUsername(),
                        a.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }
}