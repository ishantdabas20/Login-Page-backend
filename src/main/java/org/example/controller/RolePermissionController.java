package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.RolePermissionRequest;
import org.example.entity.Role;
import org.example.entity.Permission;
import org.example.service.RolePermissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RoleNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/rolesPermission")
@RequiredArgsConstructor
public class RolePermissionController {

    private final RolePermissionService rolePermissionService;


    @PostMapping("/permissions")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Role> assignPermissions(
            @RequestBody RolePermissionRequest request) throws RoleNotFoundException {

        return ResponseEntity.ok(
                rolePermissionService.assignPermissions(request)
        );
    }


    @DeleteMapping("/{roleId}/permissions/{permissionId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Role> removePermission(
            @PathVariable Long roleId,
            @PathVariable Long permissionId) throws RoleNotFoundException {

        return ResponseEntity.ok(
                rolePermissionService.removePermission(roleId, permissionId)
        );
    }


    @GetMapping("/roles")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<Permission>> getAll() {

        return ResponseEntity.ok(
                rolePermissionService.getAll()
        );
    }
}