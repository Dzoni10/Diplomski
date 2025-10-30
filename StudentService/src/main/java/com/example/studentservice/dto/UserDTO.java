package com.example.studentservice.dto;

import com.example.studentservice.domain.Role;
import com.example.studentservice.domain.StudyType;
import com.example.studentservice.domain.User;
import com.example.studentservice.validation.StrongPassword;
import com.example.studentservice.validation.ValidationConstants;
import jakarta.validation.constraints.*;


public class UserDTO {

    public int id;

    @NotBlank(message="Name is required")
    @Size(min= ValidationConstants.MIN_NAME_LENGTH,max=ValidationConstants.MAX_NAME_LENGTH
    ,message = "Name must be between {min} and {max} characters")
    @Pattern(regexp = ValidationConstants.NAME_PATTERN,
            message = ValidationConstants.NAME_INVALID_MSG)
    public String name;

    @NotBlank(message = "Surname is required")
    @Size(min = ValidationConstants.MIN_NAME_LENGTH,
            max = ValidationConstants.MAX_NAME_LENGTH,
            message = "Surname must be between {min} and {max} characters")
    @Pattern(regexp = ValidationConstants.NAME_PATTERN,
            message = ValidationConstants.NAME_INVALID_MSG)
    public String surname;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Pattern(regexp = ValidationConstants.EMAIL_PATTERN,
            message = ValidationConstants.EMAIL_INVALID_MSG)
    public String email;

    @NotBlank(message = "Password is required")
    @Size(min = ValidationConstants.MIN_PASSWORD_LENGTH,
            max = ValidationConstants.MAX_PASSWORD_LENGTH,
            message = "Password must be between {min} and {max} characters")
    @StrongPassword
    public String password;

    public Role role;

    public boolean isVerified;

    @NotBlank(message = "Index is required")
    @Size(min = ValidationConstants.MIN_INDEX_LENGTH,
            max = ValidationConstants.MAX_INDEX_LENGTH,
            message = "Index must be between {min} and {max} characters")
    @Pattern(regexp = ValidationConstants.INDEX_PATTERN,
            message = ValidationConstants.INDEX_INVALID_MSG)
    public String index;

    @NotNull(message = "Study type is required")
    public StudyType studyType;

    public UserDTO(){}

    public UserDTO(User user){
        this(user.getId(),user.getName(),user.getSurname(),user.getEmail(),user.getPassword(),user.getRole(),user.isVerified());
    }

    public UserDTO(int id, String name, String surname, String email, String password,
                   Role role, boolean isVerified){
        this.id=id;
        this.name=name;
        this.surname=surname;
        this.email=email;
        this.password=password;
        this.role=role;
        this.isVerified=isVerified;
    }

    public UserDTO(int id, String name, String surname, String email, String password,
                   Role role, boolean isVerified, String index, StudyType studyType){
        this.id=id;
        this.name=name;
        this.surname=surname;
        this.email=email;
        this.password=password;
        this.role=role;
        this.isVerified=isVerified;
        this.index=index;
        this.studyType=studyType;
    }
}
