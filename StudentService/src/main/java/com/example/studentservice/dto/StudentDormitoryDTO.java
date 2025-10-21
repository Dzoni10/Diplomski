package com.example.studentservice.dto;

import com.example.studentservice.domain.DormitoryStatus;

public class StudentDormitoryDTO {
    public String name;
    public String surname;
    public String index;
    public String email;
    public int year;
    public boolean budget;
    public double averageGrade;
    public DormitoryStatus dormitoryStatus;

    public StudentDormitoryDTO(String name, String surname, String index, String email,
                               int year, boolean budget, double averageGrade,
                               DormitoryStatus dormitoryStatus) {
        this.name = name;
        this.surname = surname;
        this.index = index;
        this.email = email;
        this.year = year;
        this.budget = budget;
        this.averageGrade = averageGrade;
        this.dormitoryStatus = dormitoryStatus;
    }

    public StudentDormitoryDTO() {}
}
