package com.example.studentservice.dto;

import com.example.studentservice.domain.StudyType;

public class StudentDormitoryPaymentDTO {
    public int id;
    public String name;
    public String surname;
    public String email;
    public int year;
    public double money;
    public StudyType studyType;
    public boolean payed;

    public StudentDormitoryPaymentDTO() {}

    public StudentDormitoryPaymentDTO(int id, String name, String surname, String email, int year,double money,StudyType studyType, boolean payed) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.year = year;
        this.money=money;
        this.studyType = studyType;
        this.payed = payed;
    }
}
