package com.example.studentservice.service;

import com.example.studentservice.domain.User;
import com.example.studentservice.repositoryInterfaces.UserRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepositoryInterface userRepository;

    @Autowired
    public UserService(UserRepositoryInterface userRepository) {
        this.userRepository = userRepository;
    }
    public User findByEmail(String email) {return userRepository.findUserByEmail(email);}
    public User save(User user){ return userRepository.save(user);}
    public boolean isEmailExist(String email){return userRepository.existsByEmail(email);}

}
