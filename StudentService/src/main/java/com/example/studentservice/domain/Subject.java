package com.example.studentservice.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="subject_table")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false)
    private int espb;

    @Column(nullable=false)
    private int semester;

    @Column(nullable=false)
    private String field;

    @ManyToOne
    @JoinColumn(name="professor_id")
    private Professor professor;


    public Subject() {}

    public Subject(String name,int espb,int semester,String field,Professor professor) {
        this.name = name;
        this.espb = espb;
        this.semester = semester;
        this.field=field;
        this.professor=professor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEspb() {
        return espb;
    }

    public void setEspb(int espb) {
        this.espb = espb;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
