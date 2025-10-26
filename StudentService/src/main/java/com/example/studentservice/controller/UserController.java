package com.example.studentservice.controller;


import com.example.studentservice.auth.AuthenticationResponse;
import com.example.studentservice.auth.JwtUtil;
import com.example.studentservice.auth.SessionRegistry;
import com.example.studentservice.domain.*;
import com.example.studentservice.dto.LogInRequest;
import com.example.studentservice.dto.StudentDormitoryDTO;
import com.example.studentservice.dto.StudentProfileDTO;
import com.example.studentservice.dto.UserDTO;
import com.example.studentservice.mapper.UserDTOMapper;
import com.example.studentservice.repositoryInterfaces.VerificationTokenRepositoryInterface;
import com.example.studentservice.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDTOMapper userDTOMapper;

    @Autowired
    private SessionRegistry sessionRegistry;

    @Autowired
    private VerificationTokenService verificationTokenService;

    @Autowired
    private CustomLoggerService loggerService;

    private VerificationTokenRepositoryInterface verificationTokenRepositoryInterface;

    //Student signup
    @PostMapping(value="/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody UserDTO studentRegistrationDTO, HttpServletRequest request)
    {
        String ipAddress = getClientIpAddress(request);
        String userAgent = request.getHeader("User-Agent");

        if(userService.isEmailExist(studentRegistrationDTO.email)) {
            loggerService.logAuthEvent(
                    "SIGNUP_FAILED",
                    studentRegistrationDTO.email,
                    "NONE",
                    "FAILURE",
                    "Email already exists",
                    ipAddress,
                    userAgent
            );
            return new ResponseEntity<>("Email exits", HttpStatus.BAD_REQUEST);
        }

        Student savedUser = new Student();
        savedUser.setEmail(studentRegistrationDTO.email);
        savedUser.setName(studentRegistrationDTO.name);
        savedUser.setSurname(studentRegistrationDTO.surname);
        savedUser.setRole(Role.STUDENT);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hash = encoder.encode(studentRegistrationDTO.password);
        savedUser.setPassword(hash);
        savedUser.setVerified(false);
        savedUser.setBudget(false);
        savedUser.setIndex(studentRegistrationDTO.index);
        savedUser.setStudyType(studentRegistrationDTO.studyType);
        savedUser.setAverageGrade(0.00);
        savedUser.setMoney(0);
        savedUser.setYear(1);
        savedUser.setDormitoryStatus(DormitoryStatus.NOT_DORMITED);
        savedUser.setBreakfast(0);
        savedUser.setLunch(0);
        savedUser.setDinner(0);

        UserDTO userDTO = new UserDTO(savedUser);
        System.out.println(userDTO.name + userDTO.email);
        try{
            userService.save(savedUser);

            userService.save(savedUser);

            String token = UUID.randomUUID().toString();
            VerificationToken verificationToken = new VerificationToken();
            verificationToken.setToken(token);
            verificationToken.setUser(savedUser);
            verificationToken.setExpiryDate(LocalDateTime.now().plusHours(24));
            verificationToken.setUsed(false);
            verificationToken.setPurpose(Purpose.VERIFICATION);

            verificationTokenService.save(verificationToken);

            String activationLink = "http://localhost:8080/api/users/verify?token=" + token;
            emailService.sendVerificationEmail(userDTO, activationLink);

            loggerService.logAuthEvent(
                    "SIGNUP_SUCCESS",
                    userDTO.email,
                    savedUser.getRole().toString(),
                    "SUCCESS",
                    "User created successfully, verification email sent",
                    ipAddress,
                    userAgent
            );

            return new ResponseEntity<>("User successefully created", HttpStatus.CREATED);


        }catch (Exception e){
            loggerService.logAuthEvent(
                    "SIGNUP_ERROR",
                    userDTO.email,
                    Role.STUDENT.toString(),
                    "ERROR",
                    "Failed to send verification email: " + e.getMessage(),
                    ipAddress,
                    userAgent
            );
            return new ResponseEntity<>("Cannot send verification email",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value="/verify")
    public ResponseEntity<String> verifyUser(@RequestParam("token") String token, HttpServletRequest request){
        String ipAddress = getClientIpAddress(request);

        VerificationToken verificationToken = verificationTokenService.findByToken(token);

        if (verificationToken == null) {
            loggerService.logAuthEvent(
                    "VERIFICATION_FAILED",
                    "UNKNOWN",
                    "UNKNOWN",
                    "FAILURE",
                    "Invalid verification token",
                    ipAddress,
                    null
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid token");
        }

        if (verificationToken.isUsed()) {
            loggerService.logAuthEvent(
                    "VERIFICATION_FAILED",
                    verificationToken.getUser().getEmail(),
                    verificationToken.getUser().getRole().toString(),
                    "FAILURE",
                    "Token already used",
                    ipAddress,
                    null
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token already used");
        }

        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            loggerService.logAuthEvent(
                    "VERIFICATION_FAILED",
                    verificationToken.getUser().getEmail(),
                    verificationToken.getUser().getRole().toString(),
                    "FAILURE",
                    "Token expired",
                    ipAddress,
                    null
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token expired");
        }

        User user = verificationToken.getUser();

        if(user == null){
            loggerService.logAuthEvent(
                    "VERIFICATION_FAILED",
                    "UNKNOWN",
                    "UNKNOWN",
                    "FAILURE",
                    "User not found",
                    ipAddress,
                    null
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found");
        }

        Purpose purpose = verificationToken.getPurpose();

        if(purpose==null) {
            purpose=Purpose.VERIFICATION;
        }

        switch (purpose) {
            case VERIFICATION:
                if (user.isVerified()) {
                    verificationToken.setUsed(true);
                    verificationTokenService.save(verificationToken);

                    loggerService.logAuthEvent(
                            "VERIFICATION_FAILED",
                            user.getEmail(),
                            user.getRole().toString(),
                            "FAILURE",
                            "User already verified",
                            ipAddress,
                            null
                    );
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already verified");
                }

                user.setVerified(true);
                userService.save(user);

                verificationToken.setUsed(true);
                verificationTokenService.save(verificationToken);

                loggerService.logAuthEvent(
                        "VERIFICATION_SUCCESS",
                        user.getEmail(),
                        user.getRole().toString(),
                        "SUCCESS",
                        "Account successfully verified",
                        ipAddress,
                        null
                );

                return ResponseEntity.ok("Account successfully verified");

            case RESET:
                if (!user.isVerified()) {
                    user.setVerified(true);
                    userService.save(user);
                }

                verificationToken.setUsed(true);
                verificationTokenService.save(verificationToken);

                loggerService.logAuthEvent(
                        "PASSWORD_RESET_CONFIRMED",
                        user.getEmail(),
                        user.getRole().toString(),
                        "SUCCESS",
                        "Password reset confirmed",
                        ipAddress,
                        null
                );

                return ResponseEntity.ok("Password reset confirmed");

            default:
                loggerService.logAuthEvent(
                        "VERIFICATION_FAILED",
                        user.getEmail(),
                        user.getRole().toString(),
                        "FAILURE",
                        "Invalid token purpose",
                        ipAddress,
                        null
                );
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid token purpose");
        }
    }

    @PostMapping(value="/login")
    public ResponseEntity<?> login(@Valid @RequestBody LogInRequest logInRequest, HttpServletRequest request){
        String ipAddress = getClientIpAddress(request);
        String userAgent = request.getHeader("User-Agent");

        User user = userService.findByEmail(logInRequest.getEmail());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if(user==null) {
            loggerService.logAuthEvent(
                    "LOGIN_FAILED",
                    logInRequest.getEmail(),
                    "UNKNOWN",
                    "FAILURE",
                    "User doesn't exist",
                    ipAddress,
                    userAgent
            );
            return new ResponseEntity<>("Unsuccessful login - user doesn't exist",HttpStatus.NOT_FOUND);
        }

        if (!encoder.matches(logInRequest.getPassword(), user.getPassword())) {
            loggerService.logAuthEvent(
                    "LOGIN_FAILED",
                    logInRequest.getEmail(),
                    user.getRole().toString(),
                    "FAILURE",
                    "Wrong password",
                    ipAddress,
                    userAgent
            );
            return new ResponseEntity<>("Unsuccessful login - wrong password", HttpStatus.UNAUTHORIZED);
        }

        if (!user.isVerified()) {
            loggerService.logAuthEvent(
                    "LOGIN_FAILED",
                    logInRequest.getEmail(),
                    user.getRole().toString(),
                    "FAILURE",
                    "Email not verified",
                    ipAddress,
                    userAgent
            );
            return new ResponseEntity<>("Unsuccessful login - mail not verified", HttpStatus.NOT_ACCEPTABLE);
        }
        String sessionId = UUID.randomUUID().toString();
        String token = jwtUtil.generateToken(user.getId(), user.getRole(), sessionId);

        sessionRegistry.registerSession(sessionId, ipAddress, userAgent, user.getId());

        loggerService.logAuthEvent(
                "LOGIN_SUCCESS",
                logInRequest.getEmail(),
                user.getRole().toString(),
                "SUCCESS",
                "User logged in successfully",
                ipAddress,
                userAgent
        );

        AuthenticationResponse response = new AuthenticationResponse(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value="/recovery")
    public ResponseEntity<?> recovery(@Valid @RequestBody LogInRequest logInRequest, HttpServletRequest request){
        String ipAddress = getClientIpAddress(request);

        User user = userService.findByEmail(logInRequest.getEmail());

        if (user == null) {
            loggerService.logAuthEvent(
                    "PASSWORD_RECOVERY_FAILED",
                    logInRequest.getEmail(),
                    "UNKNOWN",
                    "FAILURE",
                    "User doesn't exist",
                    ipAddress,
                    null
            );
            return new ResponseEntity<>("User doesn't exist", HttpStatus.NOT_FOUND);
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hash = encoder.encode(logInRequest.getPassword());
        user.setPassword(hash);
        user.setVerified(false);



        UserDTO userDTO = userDTOMapper.fromUserToDTO(user);
        userDTO.email=user.getEmail();

        try {
            userService.save(user);

            String token = UUID.randomUUID().toString();
            VerificationToken verificationToken = new VerificationToken();
            verificationToken.setToken(token);
            verificationToken.setUser(user);
            verificationToken.setExpiryDate(LocalDateTime.now().plusHours(24));
            verificationToken.setUsed(false);
            verificationToken.setPurpose(Purpose.RESET);

            verificationTokenService.save(verificationToken);

            String activationLink = "http://localhost:8080/api/users/verify?token=" + token;
            emailService.sendPasswordRecoveryEmail(userDTO, activationLink);

            loggerService.logAuthEvent(
                    "PASSWORD_RECOVERY_INITIATED",
                    logInRequest.getEmail(),
                    user.getRole().toString(),
                    "SUCCESS",
                    "Password recovery email sent",
                    ipAddress,
                    null
            );

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("message", "Successfully changed password"));
        } catch (Exception e) {
            loggerService.logAuthEvent(
                    "PASSWORD_RECOVERY_ERROR",
                    logInRequest.getEmail(),
                    user.getRole().toString(),
                    "ERROR",
                    "Failed to send recovery email: " + e.getMessage(),
                    ipAddress,
                    null
            );
            return new ResponseEntity<>("Cannot change password",HttpStatus.NOT_ACCEPTABLE);
        }
    }


    @GetMapping("/studentProfile/{id}")
    public ResponseEntity<?> getFullStudentById(@PathVariable("id") int id){
        try {
            StudentProfileDTO studentProfile = studentService.getStudentProfileById(id);
            return ResponseEntity.ok(studentProfile);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching student profile");
        }
    }

    private String getClientIpAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty()) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }
}
