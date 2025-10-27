package com.example.studentservice.service;

import com.example.studentservice.domain.Professor;
import com.example.studentservice.domain.Subject;
import com.example.studentservice.dto.ProfessorSubjectDTO;
import com.example.studentservice.repositoryInterfaces.ProfessorRepositoryInterface;
import com.example.studentservice.repositoryInterfaces.SubjectRepositoryInterface;
import org.springframework.stereotype.Service;

@Service
public class ProfessorSubjectService {

    private final ProfessorRepositoryInterface professorRepository;
    private final SubjectRepositoryInterface subjectRepository;

    public ProfessorSubjectService(ProfessorRepositoryInterface professorRepository, SubjectRepositoryInterface subjectRepository) {
        this.professorRepository = professorRepository;
        this.subjectRepository = subjectRepository;
    }

    public String assignProfessorToSubject(ProfessorSubjectDTO dto) {
        Professor professor = professorRepository.findById(dto.professorId)
                .orElseThrow(() -> new RuntimeException("Professor not found"));
        Subject subject = subjectRepository.findById(dto.subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        if (subject.getProfessor() != null && subject.getProfessor().getId() == professor.getId()) {
            throw new RuntimeException("Profesor je vec dodeljen ovom predmetu");
        }

        subject.setProfessor(professor);
        subjectRepository.save(subject);
        return "Profesor je uspesno dodeljen na predmet";
    }

}
