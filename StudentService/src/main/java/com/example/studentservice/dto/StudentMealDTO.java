package com.example.studentservice.dto;

public class StudentMealDTO {

    public String name;
    public String surname;
    public String email;
    public boolean budget;
    public double money;
    public int breakfast;
    public int lunch;
    public int dinner;

    public StudentMealDTO() {}

    public StudentMealDTO(String name,String surname,String email, boolean budget, double money, int breakfast, int lunch, int dinner) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.budget = budget;
        this.money = money;
        this.breakfast = breakfast;
        this.lunch = lunch;
        this.dinner = dinner;
    }
}
