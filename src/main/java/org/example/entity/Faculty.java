package org.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


@Entity
@Table(name = "faculty")
public class Faculty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tid;

    @NotBlank
    private String tname;

    private int tage;

    public Faculty(Long tid, String tname, int tage, String tEmail, String subject, boolean active) {
        this.tid = tid;
        this.tname = tname;
        this.tage = tage;
        this.tEmail = tEmail;
        this.subject = subject;
        this.active = active;
    }

    public Faculty() {
    }

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    @Column(unique = true, nullable = false)
    private String tEmail;
    private String subject;

    private boolean active = true;

    public String getTname() {
        return tname;
    }

    public Long getTid() {
        return tid;
    }

    public int getTage() {
        return tage;
    }

    public String gettEmail() {
        return tEmail;
    }

    public String getSubject() {
        return subject;
    }

    public boolean isActive() {
        return active;
    }

    public void setTid(Long tid) {
        this.tid = tid;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public void setTage(int tage) {
        this.tage = tage;
    }

    public void settEmail(String tEmail) {
        this.tEmail = tEmail;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}