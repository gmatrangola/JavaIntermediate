package com.matrangola.school.service;

import com.matrangola.school.dao.inmemory.InMemoryCourseDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class SpyTest {
    @Spy
    CourseService courseService = new CourseService(null, new InMemoryCourseDAO());

    @Test
    public void getCourse() {
        courseService.createCourse("MATH101", "Intro to math", 3.5f);
        Mockito.verify(courseService).createCourse("MATH101", "Intro to math", 3.5f);

        assertEquals(1, courseService.getAllCourses().size());
    }

}
