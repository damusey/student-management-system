package com.example.student.management.system.service;

import com.example.student.management.system.entity.Course;
import com.example.student.management.system.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

}
