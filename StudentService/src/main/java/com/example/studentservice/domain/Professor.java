package com.example.studentservice.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name="professor_table")
public class Professor extends User {

    @OneToMany(mappedBy = "professor")
    private List<Subject> subjects;

    public Professor(){}

    public Professor(String name, String surname, String email, String password, boolean isVerified) {
        super(name, surname, email, password, Role.PROFESSOR, isVerified);
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }
}
