package com.example.studentservice.dto;

import com.example.studentservice.domain.Subject;

import java.util.List;

public class ProfessorDTO {
    public int id;
    public String name;
    public String surname;
    public String email;
    public List<SubjectDTO> subjects;

    public ProfessorDTO() {}

    public ProfessorDTO(int id,String name, String surname, String email, List<SubjectDTO> subjects) {
        this.id=id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.subjects = subjects;
    }


}
