package com.example.studentservice.dto;

public class StudentDepositDTO {

    public int studentId;
    public double money;

    public StudentDepositDTO() {}

    public StudentDepositDTO(int studentId, double money) {
        this.studentId = studentId;
        this.money = money;
    }

}
