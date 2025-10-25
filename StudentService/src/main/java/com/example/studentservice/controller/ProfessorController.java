package com.example.studentservice.controller;

import com.example.studentservice.domain.Professor;
import com.example.studentservice.dto.ProfessorDTO;
import com.example.studentservice.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
