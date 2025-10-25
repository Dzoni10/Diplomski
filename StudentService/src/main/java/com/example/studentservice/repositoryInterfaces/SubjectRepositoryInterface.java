package com.example.studentservice.repositoryInterfaces;

import com.example.studentservice.domain.Subject;
import com.example.studentservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubjectRepositoryInterface extends JpaRepository<Subject, Integer> {
    Optional<Subject> findByIdAndProfessorId(int subjectId, int professorId);
}
