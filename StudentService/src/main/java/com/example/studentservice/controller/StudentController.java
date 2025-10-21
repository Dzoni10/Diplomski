package com.example.studentservice.controller;

import com.example.studentservice.domain.Student;
import com.example.studentservice.domain.User;
import com.example.studentservice.dto.StudentDormitoryDTO;
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

    private String getClientIpAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty()) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }

}
