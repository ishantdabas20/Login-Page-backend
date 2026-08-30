package org.example.controller;


import lombok.RequiredArgsConstructor;
import org.example.entity.Role;
import org.example.entity.Student;
import org.example.repository.RoleRepository;
import org.example.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleRepository roleRepository;
    private final RoleService roleService;

    @GetMapping("/getrole")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<Role>> getAllRole(){

            return new ResponseEntity<>(roleService.getAllRole() , HttpStatus.OK);
        }
}
