package com.example.student.management.system.controller;

import com.example.student.management.system.dto.StudentLoginRequest;
import com.example.student.management.system.entity.Student;
import com.example.student.management.system.repository.StudentRepository;
import com.example.student.management.system.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class StudentAuthController {

    private final StudentRepository studentRepository;
    private final JwtUtil jwtUtil;

    public StudentAuthController(StudentRepository studentRepository, JwtUtil jwtUtil) {
        this.studentRepository = studentRepository;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/student-login")
    public ResponseEntity<String> studentLogin(@RequestBody StudentLoginRequest request) {
        Optional<Student> studentOptional = studentRepository.findByStudentCodeAndDateOfBirth(
                request.getStudentCode(), request.getDateOfBirth());

        if (studentOptional.isEmpty()) {
            return ResponseEntity.status(401).body("Invalid student code or date of birth");
        }

        String token = jwtUtil.generateTokenForStudent(request.getStudentCode());
        return ResponseEntity.ok(token);
    }
}