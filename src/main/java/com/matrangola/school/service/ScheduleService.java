package com.matrangola.school.service;

import com.matrangola.school.dao.inmemory.SectionDAO;
import com.matrangola.school.domain.Course;
import com.matrangola.school.domain.Section;

import java.time.DayOfWeek;
import java.util.List;

public class ScheduleService {
    private SectionDAO sectionDao = new SectionDAO();

    public Section schedule(Course course, DayOfWeek ... days) {
        Section section = new Section();
        section.setCourse(course);
        section.setDaysOfWeek(days);
        return sectionDao.create(section);
    }

    public List<Section> getSections() {
        return sectionDao.getAll();
    }
}
