package com.example.studentservice.dto;

import com.example.studentservice.domain.StudyType;

public class StudentExamInfoDTO {
    public String name;
    public String surname;
    public String index;
    public int year;
    public StudyType studyType;
    public double averageGrade;
    public double money;

    public StudentExamInfoDTO() {}

    public StudentExamInfoDTO(String name,String surname,String index,int year,StudyType studyType,double averageGrade,double money) {
        this.name = name;
        this.surname = surname;
        this.index = index;
        this.year = year;
        this.studyType = studyType;
        this.averageGrade = averageGrade;
        this.money = money;
    }


}
