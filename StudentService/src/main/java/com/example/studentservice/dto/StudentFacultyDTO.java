package com.example.studentservice.dto;

import com.example.studentservice.domain.StudyType;
import com.example.studentservice.domain.Subject;

import java.util.List;

public class StudentFacultyDTO {

    public String name;
    public String surname;
    public String index;
    public String email;
    public int year;
    public boolean budget;
    public double money;
    public double averageGrade;
    public StudyType studyType;
    public List<Subject> passedSubjects;

    public StudentFacultyDTO() {}

    public StudentFacultyDTO(String name,String surname,String index,String email,int year,boolean budget,double money,double averageGrade,StudyType studyType,List<Subject> passedSubjects) {
        this.name = name;
        this.surname = surname;
        this.index = index;
        this.email = email;
        this.year = year;
        this.budget = budget;
        this.money = money;
        this.averageGrade = averageGrade;
        this.studyType = studyType;
        this.passedSubjects = passedSubjects;
    }

    public StudentFacultyDTO(String name,String surname,String index,String email,int year,boolean budget,double money,double averageGrade,StudyType studyType){
        this.name = name;
        this.surname = surname;
        this.index = index;
        this.email = email;
        this.year = year;
        this.budget = budget;
        this.money = money;
        this.averageGrade = averageGrade;
        this.studyType = studyType;
    }



}
