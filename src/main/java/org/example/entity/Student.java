package org.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


@Entity
@Table(
        name = "student",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email")
        }
)
public class Student {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @NotBlank
        private String name;

        private int age;

        @Email(message = "Invalid email format")
        @NotBlank(message = "Email is required")
        @Column(unique = true, nullable = false)
        private String email;

        private boolean active = true;

        public Student() {
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

        public void setEmail(String email) {
                this.email= email;
        }


    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}