package com.example.studentservice.repositoryInterfaces;

import com.example.studentservice.domain.Student;
import com.example.studentservice.dto.StudentDormitoryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepositoryInterface extends JpaRepository<Student, Integer> {

    @Query("SELECT new com.example.studentservice.dto.StudentDormitoryDTO(" +
            "s.name, s.surname, s.index, s.email, s.year, s.budget, s.averageGrade, s.dormitoryStatus) " +
            "FROM Student s")
    List<StudentDormitoryDTO> findAllStudentDormitoryInfo();
}
