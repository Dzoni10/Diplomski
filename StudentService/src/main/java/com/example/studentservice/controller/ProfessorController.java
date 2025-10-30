package com.example.studentservice.controller;

import com.example.studentservice.domain.Professor;
import com.example.studentservice.dto.GradeRequest;
import com.example.studentservice.dto.ProfessorDTO;
import com.example.studentservice.dto.StudentExamDTO;
import com.example.studentservice.dto.SubjectDTO;
import com.example.studentservice.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/professor")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    @GetMapping("/professors")
    public ResponseEntity<List<ProfessorDTO>> getAllProfessorsWithSubjects() {
        List<ProfessorDTO> professors = professorService.getAllProfessorsWithSubjects();
        return ResponseEntity.ok(professors);
    }

    @GetMapping("/subjects/{id}")
    public List<SubjectDTO> getSubjects(@PathVariable int id) {
        return professorService.getSubjectsForProfessor(id);
    }

    @GetMapping("/subject/students/{id}")
    public List<StudentExamDTO> getStudentsForSubject(@PathVariable int id) {
        return professorService.getRegisteredStudentsForSubject(id);
    }

    @PostMapping("/grade")
    public ResponseEntity<?> confirmGrade(@RequestBody GradeRequest req) {
        professorService.confirmGrade(req.getStudentId(), req.getSubjectId(), req.getPoints(), req.getGrade());
        return ResponseEntity.ok(Map.of("message", "Ocena je uspesno upisana"));
    }
}

