package com.example.studentservice.dto;

public class PassedExamDTO {
    public String subjectName;
    public int espb;
    public int year;
    public int points;
    public int grade;


    public PassedExamDTO() {}

    public PassedExamDTO(String subjectName, int espb, int year, int points, int grade) {
        this.subjectName = subjectName;
        this.espb = espb;
        this.year = year;
        this.points = points;
        this.grade = grade;

    }
}
