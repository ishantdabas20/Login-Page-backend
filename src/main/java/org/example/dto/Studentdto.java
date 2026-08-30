package org.example.dto;

import jakarta.validation.Valid;
import org.example.entity.Student;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class Studentdto {

    private Long id;
    private String name;
    private int age;
    private String email;

    public Studentdto() {
    }

    public Studentdto(Long id, String name, int age,String email) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email= email;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
    public String getEmail(){
        return email;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }
    public void setEmail(String email){
        this.email= email;
    }

}