package com.example.studentservice.service;

import com.example.studentservice.domain.Student;
import com.example.studentservice.domain.Subject;
import com.example.studentservice.domain.User;
import com.example.studentservice.dto.StudentDormitoryDTO;
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
                student.getDormitoryStatus(),
                student.getPassedSubjects()
        );
    }
}
