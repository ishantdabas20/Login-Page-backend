package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.Permission;
import org.example.entity.Role;
import org.example.exception.UserNotFoundException;
import org.example.repository.PermissionRepository;
import org.example.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;


    public List<Role> getAllRole() {
        List<Role> roles = roleRepository.findAll();

        if (roles.isEmpty()) {
            throw new UserNotFoundException(
                    "No permission records found"
            );
        }

        return roles;
    }

    public Role addPermissionToRole(Long roleId, Long permissionId) {

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new RuntimeException("Permission not found"));

        role.addPermission(permission);

        return roleRepository.save(role);
    }

}