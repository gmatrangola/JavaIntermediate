package com.matrangola.school.service;

import com.matrangola.school.dao.inmemory.SectionDAO;
import com.matrangola.school.domain.Course;
import com.matrangola.school.domain.Section;

import java.time.DayOfWeek;
import java.util.*;

public class ScheduleService {
    private SectionDAO sectionDao = new SectionDAO();
    private Map<String, Map<String, Section>> instructorSections = new HashMap<>();

    public Section schedule(Course course, String instructor, DayOfWeek ... days) {
        Section section = new Section(course, instructor);
        section.setDaysOfWeek(days);
        section = sectionDao.create(section);

        Map<String, Section> sched = instructorSections
                .computeIfAbsent(instructor, k -> {
                    System.out.println("Adding new " + k + " to instructor list.");
                    return new HashMap<>();
                });
        sched.put(course.getCode(), section);

        return section;
    }

    public List<Section> getSections() {
        return sectionDao.getAll();
    }

    public Set<String> getCourseCodesByInstructor(String instructor) {
        Map<String, Section> sched = instructorSections.get(instructor);
        if (sched != null) return sched.keySet();
        else return Collections.emptySet();
    }
}
