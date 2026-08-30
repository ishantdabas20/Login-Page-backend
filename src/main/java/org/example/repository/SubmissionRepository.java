package org.example.repository;

import org.example.entity.Assignment;
import org.example.entity.Submission;
import org.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {

    Optional<Submission> findByAssignmentAndStudent(Assignment assignment, User student);

    List<Submission> findByAssignment(Assignment assignment);

    List<Submission> findByStudent(User student);
}