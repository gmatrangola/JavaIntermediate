package com.matrangola.school.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matrangola.school.dao.inmemory.InMemorySectionDAO;
import com.matrangola.school.dao.inmemory.InMemorySemesterDAO;
import com.matrangola.school.domain.Course;
import com.matrangola.school.domain.Section;
import com.matrangola.school.domain.Semester;
import com.matrangola.school.domain.Student;

import java.io.File;
import java.io.IOException;
import java.time.DayOfWeek;
import java.util.List;

public class ScheduleService {
    private InMemorySemesterDAO semesterDAO;
    private InMemorySectionDAO sectionDAO;

    public ScheduleService(InMemorySemesterDAO semester, InMemorySectionDAO section) {
        semesterDAO = semester;
        sectionDAO = section;
    }

    public Semester addSemester(Semester semester) {
        semesterDAO.create(semester);
        return semester;
    }

    public Section schedule(Semester semester, Course course, String instructor, List<Student> students, DayOfWeek... daysOfWeek) {
        Section section = new Section(semester, course, daysOfWeek, 0, instructor, students);
        sectionDAO.create(section);
        return section;
    }

    public List<Section> getAllSections() {
        return sectionDAO.getAll();
    }

    public void persist(File jsonDir) throws IOException {
        for (Section section : getAllSections()) {
            persist(jsonDir, section);
        }

    }

    private void persist(File jsonDir, Section section) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(new File(jsonDir, "Section-" + section.getCourse().getCode() + ".json"), section);
    }
}
