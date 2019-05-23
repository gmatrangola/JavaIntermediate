package com.matrangola.school.service;

import com.matrangola.school.dao.inmemory.InMemorySectionDAO;
import com.matrangola.school.dao.inmemory.InMemorySemesterDAO;
import com.matrangola.school.domain.Course;
import com.matrangola.school.domain.Section;
import com.matrangola.school.domain.Semester;
import com.matrangola.school.domain.Student;

import java.time.DayOfWeek;
import java.util.List;

public class ScheduleService {
    private InMemorySemesterDAO semesterDAO = new InMemorySemesterDAO();
    private InMemorySectionDAO sectionDAO = new InMemorySectionDAO();

    public void addSemester(Semester semester) {
        semesterDAO.create(semester);
    }

    public void schedule(Semester semester, Course course, String instructor, List<Student> students, DayOfWeek... daysOfWeek) {
        Section section = new Section(semester, course, daysOfWeek, 0, instructor, students);
        sectionDAO.create(section);

    }

    public List<Section> getAllSections() {
        return sectionDAO.getAll();
    }
}
