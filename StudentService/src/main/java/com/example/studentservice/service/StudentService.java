package com.example.studentservice.service;

import com.example.studentservice.domain.ExamResult;
import com.example.studentservice.domain.Student;
import com.example.studentservice.domain.Subject;
import java.util.stream.Collectors;

import com.example.studentservice.dto.*;
import com.example.studentservice.repositoryInterfaces.ExamResultRepositoryInterface;
import com.example.studentservice.repositoryInterfaces.StudentRepositoryInterface;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class StudentService {

    private final StudentRepositoryInterface studentRepository;

    private final ExamResultRepositoryInterface examResultRepository;

    public StudentService(StudentRepositoryInterface studentRepository, ExamResultRepositoryInterface examResultRepository) {
        this.studentRepository = studentRepository;
        this.examResultRepository = examResultRepository;
    }

    public List<StudentDormitoryDTO> getAllStudentsDormitoryInfo() {
        return studentRepository.findAllStudentDormitoryInfo();
    }

    public List<Subject> getUnpassedSubjects(String email) {
        return studentRepository.findUnpassedSubjectsByEmail(email);
    }

    @Transactional
    public StudentProfileDTO getStudentProfileById(int id) {
        Student student = studentRepository.findByIdWithPassedSubjects(id);

        if (student == null) {
            throw new NoSuchElementException("Student with id " + id + " not found");
        }

        List<ExamResult> examResults = examResultRepository.findByStudentId(id);

        // Mapiraj Subject-e u PassedSubjectDTO-e
        List<PassedSubjectWithGradeDTO> passedSubjectDTOs = examResults.stream()

                        .map(s -> new PassedSubjectWithGradeDTO(
                                s.getSubject().getId(),
                                s.getSubject().getName(),
                                s.getSubject().getEspb(),
                                s.getSubject().getYear(),
                                s.getSubject().getSemester(),
                                s.getSubject().getField(),
                                s.getPoints(),
                                s.getGrade(),
                                s.getProfessor().getName()+" "+s.getProfessor().getSurname()
                        ))
                        .toList();

        return new StudentProfileDTO(
                student.getName(),
                student.getSurname(),
                student.getIndex(),
                student.getEmail(),
                student.getYear(),
                student.isBudget(),
                student.getAverageGrade(),
                student.getStudyType(),
                student.getDormitoryStatus(),
                passedSubjectDTOs
        );
    }

    public List<StudentDormitoryPaymentDTO> getAllStudentsDormitoryPayment() {
        return studentRepository.findAll().stream()
                .sorted((a, b) -> Boolean.compare(!a.isPayed(), !b.isPayed())) // true ide pre false
                .map(s -> new StudentDormitoryPaymentDTO(
                        s.getId(),
                        s.getName(),
                        s.getSurname(),
                        s.getEmail(),
                        s.getYear(),
                        s.getMoney(),
                        s.getStudyType(),
                        s.isPayed()
                ))
                .collect(Collectors.toList());
    }

    public List<StudentFacultyDTO> getAllStudentsWithPassedSubjects() {
        List<Student> students = studentRepository.findAllWithPassedSubjects();

        return students.stream()
                .map(s -> new StudentFacultyDTO(
                        s.getName(),
                        s.getSurname(),
                        s.getIndex(),
                        s.getEmail(),
                        s.getYear(),
                        s.isBudget(),
                        s.getMoney(),
                        s.getAverageGrade(),
                        s.getStudyType(),
                        s.getPassedSubjects()
                ))
                .collect(Collectors.toList());
    }

    public Student depositMoney(int studentId, double amount) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new NoSuchElementException("Student not found with id: " + studentId));

        if (amount <= 0) {
            throw new IllegalArgumentException("Iznos mora biti veci od 0");
        }

        student.setMoney(student.getMoney() + amount);
        return studentRepository.save(student);
    }

    public StudentDepositDTO getCurrentDeposit(int studentId){
        Student s = studentRepository.findById(studentId).orElseThrow(()->new RuntimeException("Student not found"));

        return new StudentDepositDTO(s.getId(),s.getMoney());
    }

    public StudentDormitoryPaymentDTO getStudentPaymentDormitory(int studentId){
        Student s = studentRepository.findById(studentId).orElseThrow(()->new RuntimeException("Student not found"));

        return new StudentDormitoryPaymentDTO(s.getId(),s.getName(),s.getSurname(),s.getEmail(),s.getYear(),s.getMoney(),s.getStudyType(),s.isPayed());
    }

    public StudentMealDTO getStudentMealInfo(int studentId) {
        Student s = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        return new StudentMealDTO(
                s.getName(),
                s.getSurname(),
                s.getEmail(),
                s.isBudget(),
                s.getMoney(),
                s.getBreakfast(),
                s.getLunch(),
                s.getDinner()
        );
    }

//    @Transactional
//    public List<PassedExamDTO> getPassedExams(int studentId) {
//        List<ExamResult> results = examResultRepository.findByStudentId(studentId);
//
//        return results.stream()
//                .map(r -> new PassedExamDTO(
//                        r.getSubject().getName(),
//                        r.getSubject().getEspb(),
//                        r.getSubject().getYear(),
//                        r.getPoints(),
//                        r.getGrade(),
//                        r.getProfessor().getName() + " " + r.getProfessor().getSurname()
//                ))
//                .collect(Collectors.toList());
//    }

    public void resetDormPayments() {
        List<Student> students = studentRepository.findAll();
        for (Student s : students) {
            if (s.isPayed()) {
                s.setPayed(false);
            }
        }
        studentRepository.saveAll(students);
    }

}
