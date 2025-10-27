package com.example.studentservice.controller;

import com.example.studentservice.dto.ExamRegistrationDTO;
import com.example.studentservice.dto.StudentExamInfoDTO;
import com.example.studentservice.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exams")
public class ExamController {

    @Autowired
    private ExamService examService;

    @GetMapping("/info/{studentId}")
    public StudentExamInfoDTO getStudentInfo(@PathVariable int studentId) {
        return examService.getStudentInfo(studentId);
    }

    @GetMapping("/available/{studentId}")
    public List<ExamRegistrationDTO> getAvailableSubjects(@PathVariable int studentId) {
        return examService.getAvailableSubjects(studentId);
    }

    @PostMapping("/register/{studentId}")
    public void registerExams(@PathVariable int studentId, @RequestBody List<Integer> subjectIds) {
        examService.registerExams(studentId, subjectIds);
    }

    @PostMapping("/unregister/{studentId}")
    public void unregisterExams(@PathVariable int studentId, @RequestBody List<Integer> subjectIds) {
        examService.unregisterExams(studentId, subjectIds);
    }
}
