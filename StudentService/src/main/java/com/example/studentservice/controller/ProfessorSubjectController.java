package com.example.studentservice.controller;

import com.example.studentservice.dto.ProfessorSubjectDTO;
import com.example.studentservice.service.ProfessorSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/professorSubject")
public class ProfessorSubjectController {

    @Autowired
    private ProfessorSubjectService professorSubjectService;

    @PostMapping("/assign")
    public ResponseEntity<?> assignProfessorToSubject(@RequestBody ProfessorSubjectDTO dto) {
        try {
            String message = professorSubjectService.assignProfessorToSubject(dto);
            return ResponseEntity.ok().body("{\"message\": \"" + message + "\"}");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("{\"message\": \"" + e.getMessage() + "\"}");
        }
    }

}
