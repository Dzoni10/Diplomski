package com.example.studentservice.service;

import com.example.studentservice.domain.Subject;
import com.example.studentservice.dto.SubjectDTO;
import com.example.studentservice.dto.SubjectProfessorListDTO;
import com.example.studentservice.repositoryInterfaces.SubjectRepositoryInterface;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubjectService {

    private final SubjectRepositoryInterface subjectRepository;

    public SubjectService(SubjectRepositoryInterface subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public List<SubjectDTO> getAllSubjects() {
        return subjectRepository.findAll()
                .stream()
                .map(s -> new SubjectDTO(
                        s.getId(),
                        s.getName(),
                        s.getEspb(),
                        s.getYear(),
                        s.getSemester(),
                        s.getField()
                ))
                .collect(Collectors.toList());
    }

    public List<SubjectProfessorListDTO> getAllSubjectsWithProfessors() {
        List<Subject> subjects = subjectRepository.findAll();

        return subjects.stream()
                .map(s -> new SubjectProfessorListDTO(
                        s.getId(),
                        s.getName(),
                        s.getEspb(),
                        s.getYear(),
                        s.getSemester(),
                        s.getField(),
                        s.getProfessor() != null ? s.getProfessor().getName() : "—",
                        s.getProfessor() != null ? s.getProfessor().getSurname() : ""
                ))
                .collect(Collectors.toList());
    }
}
