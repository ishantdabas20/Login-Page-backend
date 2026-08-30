package org.example.service;

import org.example.controller.StudentAssignmentResponse;
import org.example.controller.StudentSubmissionStatusResponse;
import org.example.controller.SubmissionResponse;
import org.example.entity.Assignment;
import org.example.entity.Submission;
import org.example.entity.User;
import org.example.exception.UserNotFoundException;
import org.example.repository.AssignmentRepository;
import org.example.repository.SubmissionRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SubmissionServiceImpl implements SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final AssignmentRepository assignmentRepository;
    private final UserRepository userRepository;

    @Value("${file.upload-dir.submissions:uploads/submissions}")
    private String uploadDir;

    public SubmissionServiceImpl(
            SubmissionRepository submissionRepository,
            AssignmentRepository assignmentRepository,
            UserRepository userRepository) {

        this.submissionRepository = submissionRepository;
        this.assignmentRepository = assignmentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<StudentAssignmentResponse> getAssignmentsForStudent(String username) {

        User student = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UserNotFoundException("Student not found"));

        List<Assignment> assignments = assignmentRepository.findAll();

        return assignments.stream()
                .map(assignment -> {

                    Optional<Submission> existing =
                            submissionRepository.findByAssignmentAndStudent(
                                    assignment,
                                    student
                            );

                    SubmissionResponse submissionResponse = existing
                            .map(s -> new SubmissionResponse(
                                    s.getId(),
                                    s.getFileName(),
                                    s.getFileUrl(),
                                    s.getSubmittedAt()
                            ))
                            .orElse(null);

                    return new StudentAssignmentResponse(
                            assignment.getId(),
                            assignment.getTitle(),
                            assignment.getSubject(),
                            assignment.getDescription(),
                            assignment.getFileName(),
                            assignment.getFileUrl(),
                            assignment.getDueDate(),
                            assignment.getCreatedBy().getUsername(),
                            existing.isPresent(),
                            submissionResponse
                    );
                })
                .collect(Collectors.toList());
    }

    @Override
    public SubmissionResponse submitAssignment(
            Long assignmentId,
            MultipartFile file,
            String username) {

        User student = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UserNotFoundException("Student not found"));

        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() ->
                        new RuntimeException("Assignment not found"));

        if (file == null || file.isEmpty()) {
            throw new NullPointerException("Submission file is empty");
        }

        try {
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String originalFileName = file.getOriginalFilename();

            if (originalFileName == null || originalFileName.isBlank()) {
                throw new NullPointerException("Invalid File Name");
            }

            String extension = "";

            int dotIndex = originalFileName.lastIndexOf('.');

            if (dotIndex >= 0) {
                extension = originalFileName.substring(dotIndex);
            }

            String newFileName = UUID.randomUUID() + extension;

            Path filePath = uploadPath.resolve(newFileName);

            Files.copy(
                    file.getInputStream(),
                    filePath,
                    StandardCopyOption.REPLACE_EXISTING
            );

            // Update existing submission or create a new one
            Submission submission = submissionRepository
                    .findByAssignmentAndStudent(assignment, student)
                    .orElse(new Submission());

            submission.setAssignment(assignment);
            submission.setStudent(student);
            submission.setFileName(originalFileName);
            submission.setFileUrl(
                    "/uploads/submissions/" + newFileName
            );

            Submission saved = submissionRepository.save(submission);

            return new SubmissionResponse(
                    saved.getId(),
                    saved.getFileName(),
                    saved.getFileUrl(),
                    saved.getSubmittedAt()
            );

        } catch (IOException e) {
            throw new RuntimeException(
                    "Failed to upload submission",
                    e
            );
        }
    }

    @Override
    public List<StudentSubmissionStatusResponse>
    getSubmissionStatusForAssignment(Long assignmentId) {

        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() ->
                        new RuntimeException("Assignment not found"));

        List<User> allStudents =
                userRepository.findByRoles_Name("STUDENT");

        List<Submission> submissions =
                submissionRepository.findByAssignment(assignment);

        return allStudents.stream()
                .map(student -> {

                    Optional<Submission> match = submissions.stream()
                            .filter(s ->
                                    s.getStudent().getId()
                                            .equals(student.getId())
                            )
                            .findFirst();

                    return new StudentSubmissionStatusResponse(
                            student.getId(),
                            student.getUsername(),
                            match.isPresent(),
                            match.map(Submission::getSubmittedAt)
                                    .orElse(null),
                            match.map(Submission::getFileName)
                                    .orElse(null),
                            match.map(Submission::getFileUrl)
                                    .orElse(null)
                    );
                })
                .collect(Collectors.toList());
    }
}
