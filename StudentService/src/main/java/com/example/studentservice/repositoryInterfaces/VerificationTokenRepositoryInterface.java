package com.example.studentservice.repositoryInterfaces;

import com.example.studentservice.domain.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepositoryInterface extends JpaRepository<VerificationToken,Integer> {
    public VerificationToken findByToken(String token);
}
