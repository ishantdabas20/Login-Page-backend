package org.example.dto;


public class Facultydto {


    private String tName;
    private int tAge;
    private String tEmail;
    private String subject;

    public Facultydto() {
    }

    public Facultydto(String tName, int tage, String tEmail, String subject) {
        this.tName = tName;
        this.tAge = tage;
        this.tEmail = tEmail;
        this.subject = subject;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public int gettAge() {
        return tAge;
    }

    public void settAge(int tAge) {
        this.tAge = tAge;
    }

    public String gettEmail() {
        return tEmail;
    }

    public void settEmail(String tEmail) {
        this.tEmail = tEmail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}