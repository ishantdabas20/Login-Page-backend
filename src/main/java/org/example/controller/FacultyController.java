package org.example.controller;


import jakarta.validation.Valid;
import org.example.dto.Facultydto;
import org.example.entity.Faculty;
import org.example.service.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/faculty")
public class FacultyController {


    @Autowired
    private FacultyService facultyservice;

    @PostMapping("/create")
    public Facultydto create(@RequestBody @Valid Facultydto dto)  {

        return facultyservice.saveFaculty(dto);
    }

    @GetMapping ("/getAll")
    @PreAuthorize("hasAnyRole('ADMIN','FACULTY')")
    public ResponseEntity<List<Faculty>> getAll() {

        return new ResponseEntity<>(facultyservice.getAll() , HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','FACULTY')")
    public Facultydto updateFaculty(@PathVariable Long id, @RequestBody Facultydto dto) {

        return facultyservice.updateFaculty(id, dto);
    }



    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','FACULTY')")
    public ResponseEntity<String> deleteFaculty(@PathVariable Long id) {

        facultyservice.deleteFaculty(id);

        return ResponseEntity.ok("Student deactivated successfully");
    }


}

