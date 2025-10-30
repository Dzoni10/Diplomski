package com.example.studentservice.service;

import com.example.studentservice.domain.ExamResult;
import com.example.studentservice.domain.Professor;
import com.example.studentservice.domain.Student;
import com.example.studentservice.domain.Subject;
import com.example.studentservice.dto.ProfessorDTO;
import com.example.studentservice.dto.StudentExamDTO;
import com.example.studentservice.dto.SubjectDTO;
import com.example.studentservice.repositoryInterfaces.ExamResultRepositoryInterface;
import com.example.studentservice.repositoryInterfaces.ProfessorRepositoryInterface;
import com.example.studentservice.repositoryInterfaces.StudentRepositoryInterface;
import com.example.studentservice.repositoryInterfaces.SubjectRepositoryInterface;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfessorService {

    private final ProfessorRepositoryInterface professorRepository;

    private final SubjectRepositoryInterface subjectRepository;

    private final StudentRepositoryInterface studentRepository;

    private final ExamResultRepositoryInterface examResultRepository;

    public ProfessorService(ProfessorRepositoryInterface professorRepository, SubjectRepositoryInterface subjectRepository, StudentRepositoryInterface studentRepository, ExamResultRepositoryInterface examResultRepository) {
        this.professorRepository = professorRepository;
        this.subjectRepository = subjectRepository;
        this.studentRepository = studentRepository;
        this.examResultRepository = examResultRepository;
    }

    public List<Professor> getAllProfessors() {
        return professorRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<ProfessorDTO> getAllProfessorsWithSubjects() {
        // Učitaj sve profesore sa predmetima
        List<Professor> professors = professorRepository.findAllWithSubjects();

        // Mapiraj ih u DTO-ove
        return professors.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private ProfessorDTO mapToDTO(Professor professor) {
        List<SubjectDTO> subjectDTOs = professor.getSubjects() == null ? List.of() :
                professor.getSubjects().stream()
                        .map(this::mapSubjectToDTO)
                        .collect(Collectors.toList());

        return new ProfessorDTO(
                professor.getId(),
                professor.getName(),
                professor.getSurname(),
                professor.getEmail(),
                subjectDTOs
        );
    }

    private SubjectDTO mapSubjectToDTO(Subject subject) {
        return new SubjectDTO(
                subject.getId(),
                subject.getName(),
                subject.getEspb(),
                subject.getYear(),
                subject.getSemester(),
                subject.getField()
        );
    }

    @Transactional
    public List<SubjectDTO> getSubjectsForProfessor(int profesorId){
        Professor p = professorRepository.findById(profesorId).orElseThrow(()-> new RuntimeException("Profesor nije pronadjen"));

        return p.getSubjects().stream().map(s->new SubjectDTO(s.getId(),s.getName(),s.getEspb(),s.getYear(),s.getSemester(),s.getField())).toList();
    }

    @Transactional
    public List<StudentExamDTO> getRegisteredStudentsForSubject(int subjectId){
        List<Student> students = studentRepository.findAll();

        return students.stream().filter(s->s.getSubjects().stream().anyMatch(sub->sub.getId() == subjectId))
                .map(s->new StudentExamDTO(s.getId(),s.getName(),s.getSurname(),s.getYear(),s.getStudyType(),s.getEmail())).toList();
    }

    @Transactional
    public void confirmGrade(int studentId, int subjectId,int points,int grade){
        Student s = studentRepository.findById(studentId).orElseThrow(()->new RuntimeException("Student nije pronadjen"));
        Subject sub = subjectRepository.findById(subjectId).orElseThrow(()->new RuntimeException("Predmet nije pronadjen"));
        Professor prof = sub.getProfessor();
        if (grade>5 && points>=51) {
            if (s.getSubjects() != null) {
                s.getSubjects().size();
                s.getSubjects().remove(sub);
            }
            if (s.getPassedSubjects() != null) {
                s.getPassedSubjects().size();
            } else {
                s.setPassedSubjects(new ArrayList<>());
            }

            s.getPassedSubjects().add(sub);

            int passedCount = s.getPassedSubjects().size();
            double newAverage = (s.getAverageGrade() * (passedCount - 1) + grade) / passedCount;
            s.setAverageGrade(newAverage);
        }
        ExamResult result = new ExamResult(s,sub,prof,points,grade);
        examResultRepository.save(result);
        studentRepository.save(s);
        studentRepository.flush();
    }

}
