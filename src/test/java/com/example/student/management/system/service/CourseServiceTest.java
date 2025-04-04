package com.example.student.management.system.service;

import com.example.student.management.system.entity.Course;
import com.example.student.management.system.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {
    @Mock
    CourseRepository courseRepository;
    CourseService courseService;

    @BeforeEach
    void setup() {
        courseService = new CourseService(courseRepository);
    }

    @Test
    void testGetAllCourses() {
        Course course1 = new Course();
        course1.setName("course1");
        Course course2 = new Course();
        course2.setName("course2");
        when(courseRepository.findAll()).thenReturn(List.of(course1, course2));
        List<Course> res = courseService.getAllCourses();
        assert res.size() == 2;
    }

    @Test
    void testGetAllCoursesEmpty() {
        when(courseRepository.findAll()).thenReturn(List.of());
        List<Course> res = courseService.getAllCourses();
        assert res.isEmpty();
    }

    @Test
    void testSaveCourse() {
        Course course = new Course();
        course.setName("course1");
        when(courseRepository.save(course)).thenReturn(course);
        Course res = courseService.saveCourse(course);
        assert res.getName().equals("course1");
    }
}
