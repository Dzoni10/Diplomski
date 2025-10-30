package com.example.studentservice.dto;

public class PassedSubjectWithGradeDTO {
    public int id;
    public String name;
    public int espb;
    public int year;
    public int semester;
    public String field;
    public int points;
    public int grade;
    public String professorName;

    public PassedSubjectWithGradeDTO() {}

    public PassedSubjectWithGradeDTO(int id, String name, int espb, int year, int semester, String field, int points, int grade, String professorName) {
        this.id = id;
        this.name = name;
        this.espb = espb;
        this.year = year;
        this.semester = semester;
        this.field = field;
        this.points = points;
        this.grade = grade;
        this.professorName = professorName;
    }
}
