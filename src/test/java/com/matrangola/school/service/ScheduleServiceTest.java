package com.matrangola.school.service;

import com.matrangola.school.dao.inmemory.InMemoryCourseDAO;
import com.matrangola.school.dao.inmemory.InMemorySectionDAO;
import com.matrangola.school.dao.inmemory.InMemorySemesterDAO;
import com.matrangola.school.domain.Course;
import com.matrangola.school.domain.Semester;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ScheduleServiceTest {

    @Mock
    InMemorySectionDAO sectionMock;

    @Mock
    InMemoryCourseDAO courseMock;

    @Mock
    InMemorySemesterDAO semesterDAO;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void schedule() {
//        courseMock = mock(InMemoryCourseDAO.class);
        Course testCourse = new Course("MATH101", "Intro to Math");
        when(courseMock.create(testCourse)).thenReturn(testCourse);
        when(courseMock.get(1)).thenReturn(testCourse);

        CourseService cs = new CourseService(courseMock);
        Course bad = cs.createCourse("Math101", "Intro to Math", 3.0f);
        assertNull(bad);

        Course course = cs.createCourse("MATH101", "Intro to Math", 3.0f);

        assertEquals("MATH101", course.getCode());

        ZonedDateTime start = ZonedDateTime.of(2020, 9, 1, 0, 0, 0, 0, ZoneId.of("America/New_York"));
        ZonedDateTime end = ZonedDateTime.of(2020, 12, 23, 0, 0, 0, 0, ZoneId.of("America/New_York"));

        ScheduleService scheduleService = new ScheduleService(semesterDAO, sectionMock);
        Semester fall = new Semester(start, end);

        when(semesterDAO.create(fall)).thenReturn(fall);

        Semester semester = scheduleService.addSemester(fall);

        assertEquals(start, semester.getStart());
        assertEquals(end, semester.getEnd());

    }
}