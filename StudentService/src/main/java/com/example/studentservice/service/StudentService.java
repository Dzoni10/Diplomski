package com.example.studentservice.service;

import com.example.studentservice.dto.StudentDormitoryDTO;
import com.example.studentservice.repositoryInterfaces.StudentRepositoryInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepositoryInterface studentRepository;

    public StudentService(StudentRepositoryInterface studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<StudentDormitoryDTO> getAllStudentsDormitoryInfo() {
        return studentRepository.findAllStudentDormitoryInfo();
    }
}
