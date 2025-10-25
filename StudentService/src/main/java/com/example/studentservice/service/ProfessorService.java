package com.example.studentservice.service;

import com.example.studentservice.domain.Professor;
import com.example.studentservice.domain.Subject;
import com.example.studentservice.dto.ProfessorDTO;
import com.example.studentservice.dto.SubjectDTO;
import com.example.studentservice.repositoryInterfaces.ProfessorRepositoryInterface;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfessorService {

    private final ProfessorRepositoryInterface professorRepository;

    public ProfessorService(ProfessorRepositoryInterface professorRepository) {
        this.professorRepository = professorRepository;
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
}
