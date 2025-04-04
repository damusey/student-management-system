package com.example.student.management.system.service;


import com.example.student.management.system.entity.Address;
import com.example.student.management.system.entity.Course;
import com.example.student.management.system.entity.Student;
import com.example.student.management.system.repository.CourseRepository;
import com.example.student.management.system.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @Transactional
    public Student updateStudentProfile(Long id, Student updatedStudent) {
        return studentRepository.findById(id).map(student -> {
            student.setEmail(updatedStudent.getEmail());
            student.setMobileNumber(updatedStudent.getMobileNumber());
            student.setParentsNames(updatedStudent.getParentsNames());

            student.getAddresses().clear();
            for (Address address : updatedStudent.getAddresses()) {
                address.setStudent(student);
                student.getAddresses().add(address);
            }

            Student st = studentRepository.save(student);
            System.out.println("student: " + st);
            return st;
        }).orElseThrow(() -> new RuntimeException("Student not found"));
    }


    public List<Course> getAssignedCourses(Long studentId) {
        return studentRepository.findById(studentId)
                .map(Student::getCourses)
                .map(ArrayList::new)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }


    @Transactional
    public void leaveCourse(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        student.getCourses().remove(course);
        studentRepository.save(student);
    }

    public Student addStudent(Student student) {
        for (Address address : student.getAddresses()) {
            address.setStudent(student);
        }
        return studentRepository.save(student);
    }

    public void assignCourseToStudent(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        student.getCourses().add(course);
        studentRepository.save(student);
    }

    public List<Student> getStudentsByName(String name) {
        return studentRepository.findByNameContainingIgnoreCase(name);
    }
}
