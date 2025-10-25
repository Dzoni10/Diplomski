package com.example.studentservice.dto;

public class SubjectProfessorListDTO {

    public int id;
    public String name;
    public int espb;
    public int year;
    public int semester;
    public String field;

    public String professorName;
    public String professorSurname;

    public SubjectProfessorListDTO() {}

    public SubjectProfessorListDTO(int id,String name,int espb,int year,int semester,String field,String professorName,String professorSurname) {
        this.id = id;
        this.name = name;
        this.espb = espb;
        this.year = year;
        this.semester = semester;
        this.field = field;
        this.professorName = professorName;
        this.professorSurname = professorSurname;
    }
}
