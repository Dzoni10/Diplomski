package com.example.studentservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class CustomLoggerService {

    private static final String BASE_LOG_DIR = "logging";
    private static final String DAYS_DIR = "days";
    private static final String AUTH_DIR = "register-login";
    private static final String DORMITORY_DIR = "dormitory";
    private static final String FACULTY_DIR = "faculty";
    private static final String MEAL_DIR = "meal";

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private final ObjectMapper objectMapper;


    public CustomLoggerService() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        initializeDirectories();
    }

    private void initializeDirectories() {
        try {
            Files.createDirectories(Paths.get(BASE_LOG_DIR, DAYS_DIR));
            Files.createDirectories(Paths.get(BASE_LOG_DIR, AUTH_DIR));
            Files.createDirectories(Paths.get(BASE_LOG_DIR, DORMITORY_DIR));
            Files.createDirectories(Paths.get(BASE_LOG_DIR, FACULTY_DIR));
            Files.createDirectories(Paths.get(BASE_LOG_DIR, MEAL_DIR));
        } catch (IOException e) {
            System.err.println("Failed to create log directories: " + e.getMessage());
        }
    }
    public void logAuthEvent(String eventType, String email, String role, String result, String message, String ipAddress, String userAgent) {
        LogEntry logEntry = createLogEntry(eventType, "AUTHENTICATION", email, role, result, message, ipAddress, userAgent);

        // Write to both universal daily log and auth-specific log
        writeToUniversalLog(logEntry);
        writeToSpecificLog(logEntry, AUTH_DIR);
    }

    public void logChangeDormitoryEvent(String eventType, String email, String role, String result, String message, String ipAddress) {
        LogEntry logEntry = createLogEntry(eventType, "DORMITORY", email, role, result, message, ipAddress, null);

        // Write to both universal daily log and password manager-specific log
        writeToUniversalLog(logEntry);
        writeToSpecificLog(logEntry, DORMITORY_DIR);
    }

    public void logChangeFacultyStatusEvent(String eventType, String email, String role, String result, String message, String ipAddress) {
        LogEntry logEntry = createLogEntry(eventType, "FACULTY", email, role, result, message, ipAddress, null);

        // Write to both universal daily log and password manager-specific log
        writeToUniversalLog(logEntry);
        writeToSpecificLog(logEntry, FACULTY_DIR);
    }

    public void logChangeMealNumberEvent(String eventType, String email, String role, String result, String message, String ipAddress) {
        LogEntry logEntry = createLogEntry(eventType, "MEAL", email, role, result, message, ipAddress, null);

        // Write to both universal daily log and password manager-specific log
        writeToUniversalLog(logEntry);
        writeToSpecificLog(logEntry, MEAL_DIR);
    }

    private static class LogEntry {
        public String timestamp;
        public String eventType;
        public String category;
        public String eventId;
        public String user;
        public String role;
        public String result;
        public String message;
        public String ipAddress;
        public String userAgent;
    }

    private LogEntry createLogEntry(String eventType, String category, String user, String role, String result, String message, String ipAddress, String userAgent) {
        LogEntry entry = new LogEntry();
        entry.timestamp = Instant.now().truncatedTo(java.time.temporal.ChronoUnit.MILLIS).toString();
        entry.eventType = eventType;
        entry.category = category;
        entry.user = user;
        entry.role = role;
        entry.result = result;
        entry.message = message;
        entry.ipAddress = ipAddress;
        entry.userAgent = userAgent;
        entry.eventId = generateEventId();
        return entry;
    }

    private void writeToUniversalLog(LogEntry logEntry) {
        String date = LocalDateTime.now().format(DATE_FORMATTER);
        String fileName = date + ".log";
        Path filePath = Paths.get(BASE_LOG_DIR, DAYS_DIR, fileName);
        writeLogEntry(filePath, logEntry);
    }

    private void writeToSpecificLog(LogEntry logEntry, String directory) {
        String date = LocalDateTime.now().format(DATE_FORMATTER);
        String fileName = date + ".log";
        Path filePath = Paths.get(BASE_LOG_DIR, directory, fileName);
        writeLogEntry(filePath, logEntry);
    }

    private void writeLogEntry(Path filePath, LogEntry logEntry) {
        try {
            File logFile = filePath.toFile();
            boolean isNewFile = !logFile.exists();

            try (FileWriter writer = new FileWriter(logFile, true)) {
                if (!isNewFile) {
                    writer.write("\n");
                }
                writer.write(objectMapper.writeValueAsString(logEntry));
                writer.write("\n");
            }

            checkAndRotateLog(filePath);

        } catch (IOException e) {
            System.err.println("Failed to write log entry: " + e.getMessage());
        }

    }


    private void checkAndRotateLog(Path filePath) throws IOException {
        long fileSizeInMB = Files.size(filePath) / (1024 * 1024);

        // Rotacija ako je fajl veći od 50MB
        if (fileSizeInMB > 50) {
            String originalName = filePath.getFileName().toString();
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmmss"));
            String rotatedName = originalName.replace(".log", "_part" + timestamp + ".log");
            Path rotatedPath = filePath.getParent().resolve(rotatedName);

            Files.move(filePath, rotatedPath);
            System.out.println("Log file rotated due to size: " + rotatedName);
        }
    }

    private String generateEventId() {
        return String.format("%d-%d",
                System.currentTimeMillis(),
                (int)(Math.random() * 1000));
    }
}
