package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.RolePermissionRequest;
import org.example.entity.Permission;
import org.example.entity.Role;
import org.example.exception.UserNotFoundException;
import org.example.repository.PermissionRepository;
import org.example.repository.RoleRepository;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RolePermissionService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;


    // Add multiple permissions to a role
    public Role assignPermissions(RolePermissionRequest request) throws RoleNotFoundException {

        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() ->
                        new RoleNotFoundException("Role not found"));


        List<Permission> permissions =
                permissionRepository.findAllById(request.getPermissionIds());


        if (permissions.size() != request.getPermissionIds().size()) {
            throw new RoleNotFoundException("Some permissions not found");
        }


        for (Permission permission : permissions) {
            role.addPermission(permission);
        }


        return roleRepository.save(role);
    }


    // Remove a single permission from a role
    public Role removePermission(Long roleId, Long permissionId) throws RoleNotFoundException {

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() ->
                        new RoleNotFoundException("Role not found"));


        Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() ->
                        new UserNotFoundException("Permission not found"));


        role.removePermission(permission);


        return roleRepository.save(role);
    }


    public List<Permission> getAll() {

        List<Permission> permissions = permissionRepository.findAll();

        if (permissions.isEmpty()) {
            throw new UserNotFoundException(
                    "No permission records found"
            );
        }

        return permissions;
    }
}