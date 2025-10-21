package com.example.studentservice;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class StudentServiceApplication implements WebMvcConfigurer {

	@Bean
	public ModelMapper getModelMapper()
	{
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(StudentServiceApplication.class, args);

		        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.matches("admin123", "$2a$10$Dow1E2F6wNwKj5oFzpFsaOeP4nK3pHz5fVQ4qP5B.YA5jE6cBQzvS"));

        String hash = encoder.encode("Scns123!");
        System.out.println(hash);
		String hash2 = encoder.encode("Ftn1234!");
		System.out.println(hash2);

	}

	@Bean
	public WebMvcConfigurer corsConfigurer(){
		return new WebMvcConfigurer(){
			@Override
			public void addCorsMappings(CorsRegistry registry){
				registry.addMapping("/api/**")
						.allowedOrigins("http://localhost:4200")
						.allowedMethods("GET","POST","PUT","DELETE","OPTIONS")
						.allowedHeaders("Authorization", "Content-Type")
						.exposedHeaders("Authorization")
						.allowCredentials(true);
			}
		};
	}
}
