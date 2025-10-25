package com.example.studentservice.controller;


import com.example.studentservice.dto.SubjectDTO;
import com.example.studentservice.dto.SubjectProfessorListDTO;
import com.example.studentservice.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/subject")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @GetMapping("/subjects")
    public ResponseEntity<List<SubjectDTO>> getAllSubjects() {
        return ResponseEntity.ok(subjectService.getAllSubjects());
    }

    @GetMapping("/with-professors")
    public ResponseEntity<List<SubjectProfessorListDTO>> getAllSubjectsWithProfessors() {
        return ResponseEntity.ok(subjectService.getAllSubjectsWithProfessors());
    }
}
