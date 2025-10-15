package com.example.studentservice.repositoryInterfaces;

import com.example.studentservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepositoryInterfce extends JpaRepository<User, Integer> {
    public Optional<User> findByEmail(String email); // METODA ZA SPRING SECURITY KORISTI SE U CUSTOM USER DETAILS SERVICE
}
