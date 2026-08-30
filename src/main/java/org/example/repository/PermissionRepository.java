package org.example.repository;

import com.fasterxml.jackson.databind.JsonNode;
import org.example.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

    Optional <Permission> findByName(String name);

}