package com.matrangola.school.domain;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Section extends RegistrationItem {
    private final Semester semester;
    private final Course course;
    private String instructor;
    private DayOfWeek[] daysOfWeek;
    private List<Student> students = new ArrayList<>();

    public Section(Semester semester, Course course, String instructor) {
        this.semester = semester;
        this.course = course;
        this.instructor = instructor;
    }

    public Course getCourse() {
        return course;
    }

    public DayOfWeek[] getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(DayOfWeek[] daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public Semester getSemester() {
        return semester;
    }

    @Override
    public String toString() {
        return "Section{" +
                "course=" + course +
                ", daysOfWeek=" + Arrays.toString(daysOfWeek) +
                ", instructor='" + instructor + '\'' +
                '}';
    }
}
