package com.example.studentservice.service;

import com.example.studentservice.domain.VerificationToken;
import com.example.studentservice.repositoryInterfaces.VerificationTokenRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationTokenService {

    private final VerificationTokenRepositoryInterface verificationTokenRepository;

    @Autowired
    public VerificationTokenService(VerificationTokenRepositoryInterface verificationTokenRepository){
        this.verificationTokenRepository = verificationTokenRepository;
    }

    public VerificationToken findByToken(String token){return verificationTokenRepository.findByToken(token);}

    public VerificationToken save(VerificationToken verificationToken){
        return verificationTokenRepository.save(verificationToken);
    }
}
