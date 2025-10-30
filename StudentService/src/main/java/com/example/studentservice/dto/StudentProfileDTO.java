package com.example.studentservice.dto;

import com.example.studentservice.domain.DormitoryStatus;
import com.example.studentservice.domain.Student;
import com.example.studentservice.domain.StudyType;
import com.example.studentservice.domain.Subject;

import java.util.List;

public class StudentProfileDTO {

    public String name;
    public String surname;
    public String index;
    public String email;
    public int year;
    public boolean budget;
    public double averageGrade;
    public StudyType studyType;
    public DormitoryStatus dormitoryStatus;
    public List<PassedSubjectWithGradeDTO> passedSubjects;

    public StudentProfileDTO() {}

    public StudentProfileDTO(String name,String surname,String index,String email, int year,boolean budget, double averageGrade,StudyType studyType, DormitoryStatus dormitoryStatus) {
        this.name = name;
        this.surname = surname;
        this.index = index;
        this.email = email;
        this.year = year;
        this.budget = budget;
        this.averageGrade = averageGrade;
        this.studyType = studyType;
        this.dormitoryStatus = dormitoryStatus;
    }

    public StudentProfileDTO(String name,String surname,String index,String email, int year,boolean budget, double averageGrade,StudyType studyType, DormitoryStatus dormitoryStatus, List<PassedSubjectWithGradeDTO> passedSubjects) {
        this.name = name;
        this.surname = surname;
        this.index = index;
        this.email = email;
        this.year = year;
        this.budget = budget;
        this.averageGrade = averageGrade;
        this.dormitoryStatus = dormitoryStatus;
        this.studyType=studyType;
        this.passedSubjects=passedSubjects;
    }



}
