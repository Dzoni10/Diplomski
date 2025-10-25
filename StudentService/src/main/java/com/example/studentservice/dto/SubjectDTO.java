package com.example.studentservice.dto;

public class SubjectDTO {
    public int id;
    public String name;
    public int espb;
    public int year;
    public int semester;
    public String field;


    public SubjectDTO() {}
    public SubjectDTO(int id,String name, int espb, int year, int semester, String field) {
        this.id=id;
        this.name = name;
        this.espb = espb;
        this.year = year;
        this.semester = semester;
        this.field = field;
    }


}
