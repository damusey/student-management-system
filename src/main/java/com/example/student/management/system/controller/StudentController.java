package com.example.student.management.system.controller;

import com.example.student.management.system.entity.Course;
import com.example.student.management.system.entity.Student;
import com.example.student.management.system.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @PreAuthorize("hasRole('STUDENT')")
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudentProfile(@PathVariable Long id, @RequestBody Student student) {
        return ResponseEntity.ok(studentService.updateStudentProfile(id, student));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('STUDENT')")
    @GetMapping("/{id}/courses")
    public ResponseEntity<List<Course>> getAssignedCourses(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getAssignedCourses(id));
    }

    @PreAuthorize("hasRole('STUDENT')")
    @DeleteMapping("/{id}/courses/{courseId}")
    public ResponseEntity<Void> leaveCourse(@PathVariable Long id, @PathVariable Long courseId) {
        studentService.leaveCourse(id, courseId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.addStudent(student));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{studentId}/course/{courseId}")
    public ResponseEntity<String> assignCourseToStudent(@PathVariable Long studentId, @PathVariable Long courseId) {
        studentService.assignCourseToStudent(studentId, courseId);
        return ResponseEntity.ok("Course assigned successfully");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/search")
    public List<Student> getStudentsByName(@RequestParam String name) {
        return studentService.getStudentsByName(name);
    }
}
