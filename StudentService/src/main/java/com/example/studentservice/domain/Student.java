package com.example.studentservice.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="student_table")
public class Student  extends User{

    @Column(name="index", nullable=false, unique=true)
    private String index;

    @Column(name="year", nullable=false)
    private int year;

    @Column(name="average_grade", nullable=false)
    private double averageGrade;

    @ManyToMany
    @JoinTable(
            name = "student_subject",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private List<Subject> subjects;

    @ManyToMany
    @JoinTable(
            name = "student_passed_subjects",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private List<Subject> passedSubjects;

    @Column(name="budget")
    private boolean budget;

    @Column(name="money")
    private double money;

    @Enumerated(EnumType.STRING)
    @Column(name="study_type", nullable=false)
    private StudyType studyType;

    @Enumerated(EnumType.STRING)
    @Column(name="dormitory_status", nullable=false)
    private DormitoryStatus dormitoryStatus;

    @Column(name="breakfast")
    private int breakfast;

    @Column(name="lunch")
    private int lunch;

    @Column(name="dinner")
    private int dinner;

    public Student(){}
    public Student(String name, String surname,String email,String password,boolean isVerified,String index, int year, double averageGrade,boolean budget,double money,StudyType studyType,DormitoryStatus dormitoryStatus,int breakfast,int lunch,int dinner) {
        super(name,surname,email,password, Role.STUDENT,isVerified);
        this.index=index;
        this.year=year;
        this.averageGrade=averageGrade;
        this.budget=budget;
        this.money=money;
        this.studyType=studyType;
        this.dormitoryStatus=dormitoryStatus;
        this.breakfast=breakfast;
        this.lunch=lunch;
        this.dinner=dinner;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getAverageGrade() {
        return averageGrade;
    }

    public void setAverageGrade(double averageGrade) {
        this.averageGrade = averageGrade;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public List<Subject> getPassedSubjects() {
        return passedSubjects;
    }

    public void setPassedSubjects(List<Subject> passedSubjects) {
        this.passedSubjects = passedSubjects;
    }

    public boolean isBudget() {
        return budget;
    }

    public void setBudget(boolean budget) {
        this.budget = budget;
    }
    public double getMoney() {
        return money;
    }
    public void setMoney(double money) {
        this.money = money;
    }

    public StudyType getStudyType() {
        return studyType;
    }

    public void setStudyType(StudyType studyType) {
        this.studyType = studyType;
    }

    public DormitoryStatus getDormitoryStatus() {
        return dormitoryStatus;
    }

    public void setDormitoryStatus(DormitoryStatus dormitoryStatus) {
        this.dormitoryStatus = dormitoryStatus;
    }

    public int getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(int breakfast) {
        this.breakfast = breakfast;
    }

    public int getLunch() {
        return lunch;
    }

    public void setLunch(int lunch) {
        this.lunch = lunch;
    }

    public int getDinner() {
        return dinner;
    }

    public void setDinner(int dinner) {
        this.dinner = dinner;
    }
}
