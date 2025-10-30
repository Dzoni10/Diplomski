package com.example.studentservice.dto;

public class PassedSubjectDTO {

    public int id;
    public String name;
    public int espb;
    public int year;
    public int semester;
    public String field;

    public PassedSubjectDTO(){}

    public PassedSubjectDTO(int id, String name, int espb, int year, int semester, String field) {
        this.id = id;
        this.name = name;
        this.espb = espb;
        this.year = year;
        this.semester = semester;
        this.field = field;
    }
}
