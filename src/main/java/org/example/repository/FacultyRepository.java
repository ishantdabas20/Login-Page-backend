package org.example.repository;

import org.example.entity.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty,Long> {

    boolean existsByTEmail(String temail);

    @Query("SELECT f FROM Faculty f WHERE f.tEmail = :tEmail")
    Optional<Faculty> findByTEmail(String tEmail);
}