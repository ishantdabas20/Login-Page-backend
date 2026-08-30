package org.example.service;

import io.micrometer.common.lang.NonNull;
import lombok.RequiredArgsConstructor;
import org.example.dto.Facultydto;
import org.example.entity.Faculty;
import org.example.entity.User;
import org.example.exception.UserNotFoundException;
import org.example.repository.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class FacultyService {


    private final FacultyRepository facultyRepository;

    // Create
    public Facultydto saveFaculty(@NonNull Facultydto dto) {


        String email = dto.gettEmail().toLowerCase();


        if (facultyRepository.existsByTEmail(email)) {

            throw new IllegalArgumentException(
                    "Faculty email already exists: " + email
            );
        }


        Faculty faculty = new Faculty();

        faculty.setTname(dto.gettName());
        faculty.setTage(dto.gettAge());
        faculty.settEmail(email);
        faculty.setSubject(dto.getSubject());


        Faculty saved = facultyRepository.save(faculty);


        return convertToDto(saved);
    }




    // Read All
    public List<Faculty> getAll() {


        List<Faculty> faculties = facultyRepository.findAll();


        if (faculties.isEmpty()) {

            throw new UserNotFoundException(
                    "No faculty records found"
            );
        }


        return faculties;
    }





    // Update
    public Facultydto updateFaculty(
            Long tid,
            @NonNull Facultydto dto
    ) {


        Faculty faculty = facultyRepository.findById(tid)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "Faculty not found with id: " + tid
                        )
                );



        String email = dto.gettEmail().toLowerCase();


        if (!faculty.gettEmail().equals(email)
                && facultyRepository.existsByTEmail(email)) {

            throw new IllegalArgumentException(
                    "Faculty email already exists"
            );
        }



        faculty.settEmail(email);
        faculty.setTname(dto.gettName());
        faculty.setTage(dto.gettAge());
        faculty.setSubject(dto.getSubject());



        Faculty updated = facultyRepository.save(faculty);


        return convertToDto(updated);

    }





    // Delete
    public void deleteFaculty(Long tid) {


        Faculty faculty = facultyRepository.findById(tid)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "Faculty not found with id: " + tid
                        )
                );


        facultyRepository.delete(faculty);

    }





    public void addUser(User user) {

    }





    private Facultydto convertToDto(Faculty faculty) {


        Facultydto dto = new Facultydto();

        dto.settName(faculty.getTname());
        dto.settAge(faculty.getTage());
        dto.settEmail(faculty.gettEmail());
        dto.setSubject(faculty.getSubject());


        return dto;
    }

}