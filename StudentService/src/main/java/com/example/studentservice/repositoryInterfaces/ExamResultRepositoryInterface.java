package com.example.studentservice.repositoryInterfaces;

import com.example.studentservice.domain.ExamResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExamResultRepositoryInterface extends JpaRepository<ExamResult, Integer> {
    @Query("SELECT e FROM ExamResult e JOIN FETCH e.professor JOIN FETCH e.subject WHERE e.student.id = :studentId")
    List<ExamResult> findByStudentId(@Param("studentId") int studentId);
}
