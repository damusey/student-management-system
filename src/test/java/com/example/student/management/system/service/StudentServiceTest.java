package com.example.student.management.system.service;

import com.example.student.management.system.entity.Address;
import com.example.student.management.system.entity.Course;
import com.example.student.management.system.entity.Student;
import com.example.student.management.system.repository.CourseRepository;
import com.example.student.management.system.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    private StudentService studentService;

    @Mock
    CourseRepository courseRepository;

    @BeforeEach
    void setUp() {
        studentService = new StudentService(studentRepository, courseRepository);
    }

    @Test
    void testAddStudent() {
        Set<Address> addressesSet = new HashSet<>();
        addressesSet.add(Address.builder().country("testCountry").build());
        Student student = new Student();
        Address address = new Address();
        student.setAddresses(addressesSet);
        address.setStudent(student);

        when(studentRepository.save(student)).thenReturn(student);

        Student savedStudent = studentService.addStudent(student);

        assertNotNull(savedStudent);
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    void testUpdateStudentProfile() {
        Set<Address> addressesSet = new HashSet<>();
        addressesSet.add(Address.builder().country("testCountry").build());
        Long studentId = 1L;
        Student existingStudent = new Student();
        existingStudent.setId(studentId);
        Student updated = new Student();
        updated.setEmail("test@test.com");
        updated.setMobileNumber("9999999999");
        updated.setParentsNames(List.of("testName"));
        updated.setAddresses(addressesSet);

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(existingStudent));
        when(studentRepository.save(any(Student.class))).thenReturn(existingStudent);

        Student result = studentService.updateStudentProfile(studentId, updated);

        assertNotNull(result);
        assertEquals("test@test.com", result.getEmail());
        verify(studentRepository).save(existingStudent);
    }

    @Test
    void testAssignCourseToStudent() {
        Long studentId = 1L;
        Long courseId = 2L;
        Student student = new Student();
        Course course = new Course();

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        studentService.assignCourseToStudent(studentId, courseId);

        assertTrue(student.getCourses().contains(course));
        verify(studentRepository).save(student);
    }

    @Test
    void testLeaveCourse() {
        Long studentId = 1L;
        Long courseId = 2L;
        Student student = new Student();
        Course course = new Course();
        student.setCourses(new HashSet<>(List.of(course)));

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        studentService.leaveCourse(studentId, courseId);

        assertFalse(student.getCourses().contains(course));
        verify(studentRepository).save(student);
    }

    @Test
    void testGetStudentsByName() {
        String name = "testName";
        List<Student> expected = List.of(new Student());
        when(studentRepository.findByNameContainingIgnoreCase(name)).thenReturn(expected);

        List<Student> result = studentService.getStudentsByName(name);

        assertEquals(expected.size(), result.size());
        verify(studentRepository).findByNameContainingIgnoreCase(name);
    }

    @Test
    void testGetAssignedCourses() {
        Long studentId = 1L;
        Student student = new Student();
        Course course = new Course();
        student.setCourses(Set.of(course));

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));

        List<Course> courses = studentService.getAssignedCourses(studentId);

        assertEquals(1, courses.size());
        assertTrue(courses.contains(course));
    }

    @Test
    void testUpdateStudentProfile_NotFound() {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> studentService.updateStudentProfile(1L, new Student()));
    }

    @Test
    void testGetAssignedCoursesNotFound() {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> studentService.getAssignedCourses(1L));
    }

    @Test
    void testLeaveCourseWhereStudentNotFound() {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> studentService.leaveCourse(1L, 2L));
    }

    @Test
    void testLeaveCourseWhereCourseNotFound() {
        Student student = new Student();
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));
        when(courseRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> studentService.leaveCourse(1L, 2L));
    }

    @Test
    void testAssignCourseToStudentNotFound() {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> studentService.assignCourseToStudent(1L, 2L));
    }
}
