package com.example.studentservice.dto;

import com.example.studentservice.domain.StudyType;

public class StudentExamDTO {

    public int studentId;
    public String name;
    public String surname;
    public int year;
    public StudyType studyType;
    public String email;
    public Integer points;
    public Integer grade;

    public StudentExamDTO(){}

    public StudentExamDTO(int studentId,String name,String surname,int year,StudyType studyType,String email) {
        this.studentId = studentId;
        this.name = name;
        this.surname = surname;
        this.year = year;
        this.studyType = studyType;
        this.email = email;
    }
}
