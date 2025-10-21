package com.example.studentservice.service;

import com.example.studentservice.config.CustomUserDetails;
import com.example.studentservice.domain.User;
import com.example.studentservice.repositoryInterfaces.UserRepositoryInterface;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService  implements UserDetailsService {

    private final UserRepositoryInterface userRepositoryInterface;

    public CustomUserDetailService(UserRepositoryInterface userRepositoryInterface){
        this.userRepositoryInterface = userRepositoryInterface;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepositoryInterface.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("User not found"));
        return new CustomUserDetails(user);
    }
}
