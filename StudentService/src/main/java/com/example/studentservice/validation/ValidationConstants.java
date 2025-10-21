package com.example.studentservice.validation;

public class ValidationConstants {

    // User validation patterns
    public static final String NAME_PATTERN = "^[a-zA-Z0-9À-ž\\s\\-'@]+$";
    public static final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    // Index pattern
    public static final String INDEX_PATTERN = "^(?:[A-Z]{2}|[A-Z][0-9])-(?:[1-9]|[1-9][0-9]|1[0-9]{2}|2[0-3][0-9]|240)-20(1[1-9]|[2-9][0-9])$\n";


    // Size limits
    public static final int MIN_NAME_LENGTH = 2;
    public static final int MAX_NAME_LENGTH = 50;
    public static final int MIN_PASSWORD_LENGTH = 8;
    public static final int MAX_PASSWORD_LENGTH = 128;
    public static final int MIN_INDEX_LENGTH = 9;
    public static final int MAX_INDEX_LENGTH = 11;


    // Error messages
    public static final String NAME_INVALID_MSG = "Name must contain only letters, numbers, spaces, hyphens, apostrophes and @";
    public static final String EMAIL_INVALID_MSG = "Invalid email format";
    public static final String INDEX_INVALID_MSG = "Invalid index format";

    private ValidationConstants() {
        // Prevent instantiation
    }
}
