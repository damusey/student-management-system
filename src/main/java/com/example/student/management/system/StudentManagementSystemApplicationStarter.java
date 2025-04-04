package com.example.student.management.system;

import com.example.student.management.system.entity.Role;
import com.example.student.management.system.entity.User;
import com.example.student.management.system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@SpringBootApplication
@Slf4j
public class StudentManagementSystemApplicationStarter {

	public static void main(String[] args) {
		SpringApplication.run(StudentManagementSystemApplicationStarter.class, args);
	}

	/**
	 * This method loads an entry of admin user into the database if it does not exist.
	 */
	@Bean
	CommandLineRunner initAdminUser(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			if (userRepository.findByUsername("admin").isEmpty()) {
				User admin = User.builder()
						.username("admin")
						.password(passwordEncoder.encode("password"))
						.role(Role.ADMIN)
						.build();
				userRepository.save(admin);
				log.info("Admin user created with username: admin and password: password");
			}
		};
	}
}
