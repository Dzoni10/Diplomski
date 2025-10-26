package com.example.studentservice.service;

import com.example.studentservice.domain.Student;
import com.example.studentservice.domain.Subject;
import java.util.stream.Collectors;
import com.example.studentservice.dto.StudentDormitoryDTO;
import com.example.studentservice.dto.StudentFacultyDTO;
import com.example.studentservice.dto.StudentMealDTO;
import com.example.studentservice.dto.StudentProfileDTO;
import com.example.studentservice.repositoryInterfaces.StudentRepositoryInterface;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class StudentService {

    private final StudentRepositoryInterface studentRepository;

    public StudentService(StudentRepositoryInterface studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<StudentDormitoryDTO> getAllStudentsDormitoryInfo() {
        return studentRepository.findAllStudentDormitoryInfo();
    }

    public StudentProfileDTO getStudentProfile(String email) {
        StudentProfileDTO dto = studentRepository.findStudentProfileByEmail(email);
        if (dto != null) {
            Student student = studentRepository.findStudentByEmail(email); // ako imaš ovu metodu
            dto.passedSubjects = student.getPassedSubjects(); // dodaj ručno
        }
        return dto;
    }

    public List<Subject> getUnpassedSubjects(String email) {
        return studentRepository.findUnpassedSubjectsByEmail(email);
    }

    public StudentProfileDTO getStudentProfileById(int id) {
        Student student = studentRepository.findByIdWithPassedSubjects(id);

        if (student == null) {
            throw new NoSuchElementException("Student with id " + id + " not found");
        }

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
                student.getPassedSubjects()
        );
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

}
