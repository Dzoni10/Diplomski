package com.example.studentservice.config;

import com.example.studentservice.auth.JwtAuthenticationFilter;
import com.example.studentservice.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> {}) // koristi globalni WebMvcConfigurer ako ga imaš
                .authorizeHttpRequests(auth -> auth
                        // Swagger potpuno otvoren
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**",
                                "/swagger-resources/**",
                                "/webjars/**"
                        ).permitAll()

                        // Public endpoints
                        .requestMatchers(HttpMethod.POST, "/api/users/signup").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/users/verify").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/users/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/student/dormitory").hasRole("ADMINSCNS")
                        .requestMatchers(HttpMethod.PUT, "/api/users/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/users/studentProfile/**").hasRole("STUDENT")
                        .requestMatchers(HttpMethod.GET, "/api/student/faculty/students").hasRole("ADMINFAKS")
                        .requestMatchers(HttpMethod.PUT, "/api/student/changeFacultyStatus").hasRole("ADMINFAKS")
                        .requestMatchers(HttpMethod.GET, "/api/subject/subjects").hasRole("ADMINFAKS")
                        .requestMatchers(HttpMethod.GET, "/api/professor/professors").hasRole("ADMINFAKS")
                        .requestMatchers(HttpMethod.POST, "/api/professorSubject/assign").hasRole("ADMINFAKS")
                        .requestMatchers(HttpMethod.GET, "/api/subject/with-professors").hasRole("ADMINFAKS")

                        // Sve ostalo traži auth
                        .anyRequest().authenticated()
                )
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(httpBasic -> {})
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authProvider(CustomUserDetailService userDetailService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

}
