package com.example.studentservice.controller;

import com.example.studentservice.domain.Student;
import com.example.studentservice.domain.Subject;
import com.example.studentservice.domain.User;
import com.example.studentservice.dto.StudentDormitoryDTO;
import com.example.studentservice.dto.StudentFacultyDTO;
import com.example.studentservice.dto.StudentMealDTO;
import com.example.studentservice.dto.StudentProfileDTO;
import com.example.studentservice.service.CustomLoggerService;
import com.example.studentservice.service.StudentService;
import com.example.studentservice.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private UserService userService;

    @Autowired
    private CustomLoggerService loggerService;


    @GetMapping("/dormitory")
    public ResponseEntity<List<StudentDormitoryDTO>> getAllStudentsDormitoryInfo() {
        List<StudentDormitoryDTO> students = studentService.getAllStudentsDormitoryInfo();
        return ResponseEntity.ok(students);
    }

    @PutMapping("/changeStatus")
    public ResponseEntity<?> changeDormitoryStatus(@RequestBody @Validated StudentDormitoryDTO studentDormitoryDTO, HttpServletRequest request) {

        String ipAddress = getClientIpAddress(request);

       if(studentDormitoryDTO==null)
       {
           loggerService.logChangeDormitoryEvent(
                   "FAILED_CHANGE",
                   "scns@gmail.com",
                   "ADMINSCNS",
                   "FAILURE",
                   "CANNOT CHANGE DORMITORY STATUS",
                   ipAddress
           );
           return new ResponseEntity<>("Unsuccessful change dormitory status", HttpStatus.INTERNAL_SERVER_ERROR);
       }
            User user = userService.findByEmail(studentDormitoryDTO.email);

            if (user == null) {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }

            Student student = (Student) user;
            student.setDormitoryStatus(studentDormitoryDTO.dormitoryStatus);

            userService.save(student);

            loggerService.logChangeDormitoryEvent(
                    "SUCCESSFULL CHANGE",
                    "scns@gmail.com",
                    "ADMINSCNS",
                    "SUCCESS",
                    "SUCCESSFULL CHANGE DORMITORY STATUS",
                    ipAddress
            );
            return new ResponseEntity<>("Successfull change dormitory status", HttpStatus.OK);
    }

    @PutMapping("/changeMealNumber")
    public ResponseEntity<?> changeMealNumber(@RequestBody @Validated StudentMealDTO studentMealDTO, HttpServletRequest request) {

        String ipAddress = getClientIpAddress(request);

        if(studentMealDTO==null)
        {
            loggerService.logChangeMealNumberEvent(
                    "FAILED_CHANGE",
                    studentMealDTO.email,
                    "STUDENT",
                    "FAILURE",
                    "CANNOT CHANGE MEAL NUMBER",
                    ipAddress
            );
            return new ResponseEntity<>("Unsuccessful change meal number", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        User user = userService.findByEmail(studentMealDTO.email);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Unsuccessful change student status"));
        }

        Student student = (Student) user;
        student.setBreakfast(studentMealDTO.breakfast);
        student.setLunch(studentMealDTO.lunch);
        student.setDinner(studentMealDTO.dinner);
        student.setMoney(studentMealDTO.money);

        userService.save(student);

        loggerService.logChangeMealNumberEvent(
                "SUCCESSFULL CHANGE",
                studentMealDTO.email,
                "STUDENT",
                "SUCCESS",
                "SUCCESSFULL CHANGE MEAL NUMBER",
                ipAddress
        );
        return ResponseEntity.ok(Map.of("message", "Successfull change meal number"));
    }


    @GetMapping("/faculty/students")
    public ResponseEntity<?> getAllStudentsWithPassedSubjects() {
        try {
            List<StudentFacultyDTO> students = studentService.getAllStudentsWithPassedSubjects();
            return ResponseEntity.ok(students);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching students with passed subjects: " + e.getMessage());
        }
    }

    @PutMapping("/changeFacultyStatus")
    public ResponseEntity<?> changeFacultyStatus(@RequestBody @Validated StudentFacultyDTO studentFacultyDTO, HttpServletRequest request) {

        String ipAddress = getClientIpAddress(request);

        if(studentFacultyDTO==null)
        {
            loggerService.logChangeDormitoryEvent(
                    "FAILED_CHANGE_STUDENT_STATUS",
                    "ftn@gmail.com",
                    "ADMINFAKS",
                    "FAILURE",
                    "CANNOT CHANGE STUDENT STATUS",
                    ipAddress
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Unsuccessful change student status"));
        }
        User user = userService.findByEmail(studentFacultyDTO.email);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "User not found"));
        }

        Student student = (Student) user;
        student.setBudget(studentFacultyDTO.budget);
        student.setYear(studentFacultyDTO.year);
        student.setStudyType(studentFacultyDTO.studyType);

        userService.save(student);

        loggerService.logChangeDormitoryEvent(
                "SUCCESSFULL CHANGE STUDENT STATUS",
                "ftn@gmail.com",
                "ADMINFAKS",
                "SUCCESS",
                "SUCCESSFULL CHANGE STUDENT STATUS",
                ipAddress
        );
        return ResponseEntity.ok(Map.of("message", "Successfull change student status"));
    }

    @GetMapping("/unpassed/{email}")
    public ResponseEntity<List<Subject>> getUnpassedSubjects(@PathVariable String email) {
        List<Subject> unpassed = studentService.getUnpassedSubjects(email);
        return new ResponseEntity<>(unpassed, HttpStatus.OK);
    }

    @GetMapping("/meal-info/{id}")
    public ResponseEntity<StudentMealDTO> getStudentMealInfo(@PathVariable int id) {
        return ResponseEntity.ok(studentService.getStudentMealInfo(id));
    }

    private String getClientIpAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty()) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }
}
