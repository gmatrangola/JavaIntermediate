package com.matrangola.school.service;

import com.matrangola.school.dao.inmemory.SectionDAO;
import com.matrangola.school.domain.Course;
import com.matrangola.school.domain.Section;

import java.time.DayOfWeek;
import java.util.*;

public class ScheduleService {
    private SectionDAO sectionDao = new SectionDAO();
    private Map<String, Map<String, List<Section>>> instructorSections = new HashMap<>();

    public Section schedule(Course course, String instructor, DayOfWeek ... days) {
        Section section = new Section(course, instructor);
        section.setDaysOfWeek(days);
        section = sectionDao.create(section);

        Map<String, List<Section>> sched = instructorSections
                .computeIfAbsent(instructor, k -> new HashMap<>());
        List<Section> sections = sched.computeIfAbsent(course.getCode(), c -> new ArrayList<>());
        sections.add(section);
        sched.put(course.getCode(), sections);

        return section;
    }

    public List<Section> getSections() {
        return sectionDao.getAll();
    }

    public Set<String> getCourseCodesByInstructor(String instructor) {
        Map<String, List<Section>> sched = instructorSections.get(instructor);
        if (sched != null) return sched.keySet();
        else return Collections.emptySet();
    }

    public List<Section> getSectionsByInstructor(String instructor) {
        Collection<List<Section>> list = instructorSections.get(instructor).values();
        List<Section> all = new ArrayList<>();
        for (List<Section> sections : list) {
            all.addAll(sections);
        }
        return all;
    }
}
