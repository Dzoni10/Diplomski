package com.example.studentservice.repositoryInterfaces;

import com.example.studentservice.domain.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProfessorRepositoryInterface extends JpaRepository<Professor, Integer> {

    @Query("SELECT DISTINCT p FROM Professor p LEFT JOIN FETCH p.subjects")
    List<Professor> findAllWithSubjects();

}
