package com.example.student.management.system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentLoginRequest {
    private String studentCode;
    private LocalDate dateOfBirth;
}
