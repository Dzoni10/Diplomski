package com.example.studentservice.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="exam_result")
public class ExamResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name="student_id",nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name="subject_id",nullable = false)
    private Subject subject;

    @ManyToOne
    @JoinColumn(name="professor_id",nullable = false)
    private Professor professor;

    @Column(nullable = false)
    private int points;

    @Column(nullable = false)
    private int grade;

    private LocalDate dateGraded = LocalDate.now();

    public ExamResult() {}

    public ExamResult(Student student,Subject subject,Professor professor,int points,int grade) {
        this.student = student;
        this.subject = subject;
        this.professor = professor;
        this.points = points;
        this.grade = grade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public LocalDate getDateGraded() {
        return dateGraded;
    }

    public void setDateGraded(LocalDate dateGraded) {
        this.dateGraded = dateGraded;
    }
}
