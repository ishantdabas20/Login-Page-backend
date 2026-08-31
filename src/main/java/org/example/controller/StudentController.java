package org.example.controller;


import jakarta.transaction.SystemException;
import jakarta.validation.Valid;
import org.example.dto.Facultydto;
import org.example.dto.Studentdto;
import org.example.entity.Faculty;
import org.example.entity.Student;
import org.example.entity.User;
import org.example.service.FacultyService;
import org.example.service.Studentservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {


    private Studentservice studentservice;

    @PostMapping("/addUser")
    public Studentdto create(@RequestBody @Valid Studentdto dto) throws SystemException {

        return studentservice.save(dto);
    }

    @GetMapping ("/getAll")
    @PreAuthorize("hasAnyRole('ADMIN','STUDENT')")
    public ResponseEntity<List<Student>> getAll() {

        return new ResponseEntity<>(studentservice.getAll() , HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','STUDENT')")
    public Studentdto update(@PathVariable Long id, @RequestBody Studentdto dto) {

        return studentservice.update(id, dto);
    }

    //  @DeleteMapping("/{id}")
    //public void delete(@PathVariable Long id) {
    //     service.delete(id);
    //}

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','STUDENT')")
    public ResponseEntity delete(@PathVariable Long id) {

        studentservice.deleteStudent(id);

        return ResponseEntity.ok("Student deactivated successfully");
    }


}

