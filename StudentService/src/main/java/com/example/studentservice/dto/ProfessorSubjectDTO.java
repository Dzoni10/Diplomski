package com.example.studentservice.dto;

public class ProfessorSubjectDTO {

    public int professorId;
    public int subjectId;

    public ProfessorSubjectDTO() {}

    public ProfessorSubjectDTO(int professorId, int subjectId) {
        this.professorId = professorId;
        this.subjectId = subjectId;
    }
}
