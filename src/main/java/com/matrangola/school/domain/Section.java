package com.matrangola.school.domain;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Section implements Item {
    private DayOfWeek[] days;
    private Date time;
    private int room;
    private String instructor;
    private List<Student> students;
    private int id;

    public Section(DayOfWeek[] days, int room, String instructor, List<Student> students) {
        this.days = days;
        this.room = room;
        this.instructor = instructor;
        this.students = students;
    }

    public DayOfWeek[] getDays() {
        return days;
    }

    public void setDays(DayOfWeek[] days) {
        this.days = days;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Section{" +
                "days=" + Arrays.toString(days) +
                ", time=" + time +
                ", room=" + room +
                ", instructor='" + instructor + '\'' +
                ", students=" + students +
                ", id=" + id +
                '}';
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }
}
