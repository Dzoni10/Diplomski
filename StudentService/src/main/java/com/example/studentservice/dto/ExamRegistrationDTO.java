package com.example.studentservice.dto;

public class ExamRegistrationDTO {
    public int subjectId;
    public String subjectName;
    public int espb;
    public int year;
    public String field;
    public String professorName;
    public String professorSurname;
    public String professorEmail;
    public boolean registered;

    public ExamRegistrationDTO(){}

    public ExamRegistrationDTO(int subjectId,String subjectName,int espb,int year,String field,String professorName,String professorSurname,String professorEmail,boolean registered){
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.espb = espb;
        this.year = year;
        this.field = field;
        this.professorName = professorName;
        this.professorSurname = professorSurname;
        this.professorEmail = professorEmail;
        this.registered = registered;
    }
}
