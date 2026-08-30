package org.example.service;

import org.example.dto.Studentdto;
import org.example.entity.Student;
import org.example.entity.User;
import org.example.exception.UserNotFoundException;
import org.example.repository.StudentRepository;
import org.example.repository.UserRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class Studentservice {


    private final StudentRepository repository;
    private final UserRepository userRepository;


    public Studentservice(StudentRepository repository,
                          UserRepository userRepository) {

        this.repository = repository;
        this.userRepository = userRepository;
    }

    // Create
    public Studentdto save(@NonNull Studentdto dto) {


        String email = dto.getEmail().toLowerCase();



        if (repository.existsByEmail(email)) {

            throw new IllegalArgumentException(
                    "Student email already exists: " + email
            );
        }



        Student student = new Student();

        student.setName(dto.getName());
        student.setAge(dto.getAge());
        student.setEmail(email);



        Student saved = repository.save(student);



        return convertToDto(saved);
    }

    // Read
    public List<Student> getAll() {


        List<Student> students = repository.findAll();


        if(students.isEmpty()) {

            throw new UserNotFoundException(
                    "No student records found"
            );
        }


        return students;
    }

    // Update
    public Studentdto update(Long id,
                             @NonNull Studentdto dto) {


        Student student = repository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "Student not found with id: " + id
                        )
                );



        if(dto.getEmail() != null &&
                !dto.getEmail().isBlank() &&
                !student.getEmail()
                        .equalsIgnoreCase(dto.getEmail())) {


            String email = dto.getEmail().toLowerCase();


            if(repository.existsByEmail(email)) {

                throw new IllegalArgumentException(
                        "Student email already exists"
                );
            }


            student.setEmail(email);
        }



        student.setName(dto.getName());
        student.setAge(dto.getAge());



        Student updated = repository.save(student);



        return convertToDto(updated);
    }

    // Delete
    public void deleteStudent(Long id) {


        Student student = repository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "Student not found with id: " + id
                        )
                );


        student.setActive(false);


        repository.save(student);

    }





    private Studentdto convertToDto(Student student) {


        Studentdto response = new Studentdto();


        response.setId(student.getId());
        response.setName(student.getName());
        response.setAge(student.getAge());
        response.setEmail(student.getEmail());


        return response;
    }

}