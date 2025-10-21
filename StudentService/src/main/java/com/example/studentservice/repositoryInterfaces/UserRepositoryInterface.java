package com.example.studentservice.repositoryInterfaces;

import com.example.studentservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryInterface extends JpaRepository<User, Integer> {
    public User findUserByEmail(String email);
    public Optional<User> findByEmail(String email); // METODA ZA SPRING SECURITY KORISTI SE U CUSTOM USER DETAILS SERVICE
    public boolean existsByEmail(String email);
    public User findUserById(int id);
    public List<User> findUsersByRole(String role);
}
