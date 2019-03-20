package com.matrangola.school.service;

import com.matrangola.school.dao.inmemory.InMemoryCourseDAO;
import com.matrangola.school.domain.Course;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class CourseServiceTest {

    @Mock
    InMemoryCourseDAO daoMock;

//    @Rule
//    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void createCourse() {
        Course testCourse = new Course("MATH101", "Intro to Math");
        when(daoMock.create(testCourse)).thenReturn(testCourse);
        when(daoMock.get(1)).thenReturn(testCourse);

        CourseService cs = new CourseService(null, daoMock);
        Course course = cs.createCourse("MATH101", "Intro to Math", 3.0f);

        assertEquals("MATH101", course.getCode());
    }

    @Test
    public void createCourse1() {
    }

    @Test
    public void getCourseByCode() {
        Course testCourse = new Course("MATH101", "Intro to Math");
        List<Course> testList = Arrays.asList(testCourse);
        when(daoMock.getAll()).thenReturn(testList);
        CourseService cs = new CourseService(null, daoMock);

        Course course = cs.getCourseByCode("MATH101");
        assertEquals("MATH101", course.getCode());

        Course course2 = cs.getCourseByCode("MATH2");
        assertNull(course2);    }

    @Test
    public void getAllCourses() {
    }
}