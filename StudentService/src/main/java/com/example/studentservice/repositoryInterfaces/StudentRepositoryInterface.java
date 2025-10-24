package com.example.studentservice.repositoryInterfaces;

import com.example.studentservice.domain.Student;
import com.example.studentservice.domain.Subject;
import com.example.studentservice.dto.StudentDormitoryDTO;
import com.example.studentservice.dto.StudentProfileDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepositoryInterface extends JpaRepository<Student, Integer> {


    Student findStudentByEmail(String email);
    Student findStudentById(int id);

    @Query("SELECT new com.example.studentservice.dto.StudentDormitoryDTO(" +
            "s.name, s.surname, s.index, s.email, s.year, s.budget, s.averageGrade,s.studyType, s.dormitoryStatus) " +
            "FROM Student s")
    List<StudentDormitoryDTO> findAllStudentDormitoryInfo();


    @Query("SELECT new com.example.studentservice.dto.StudentProfileDTO(" +
            "s.name, s.surname, s.index, s.email, s.year, s.budget, s.averageGrade,s.studyType, s.dormitoryStatus) " +
            "FROM Student s WHERE s.email = :email")
    StudentProfileDTO findStudentProfileByEmail(@Param("email") String email);

    // Predmeti koje student još NIJE položio
    @Query("SELECT subj FROM Student s JOIN s.subjects subj " +
            "WHERE s.email = :email AND subj NOT IN (SELECT ps FROM Student st JOIN st.passedSubjects ps WHERE st.email = :email)")
    List<Subject> findUnpassedSubjectsByEmail(@Param("email") String email);

    @Query("SELECT s FROM Student s LEFT JOIN FETCH s.passedSubjects WHERE s.id = :id")
    Student findByIdWithPassedSubjects(@Param("id") int id);

    @Query("SELECT DISTINCT s FROM Student s LEFT JOIN FETCH s.passedSubjects")
    List<Student> findAllWithPassedSubjects();
}
