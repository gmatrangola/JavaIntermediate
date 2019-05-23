package com.matrangola.school.domain;

import java.time.ZonedDateTime;

public class Semester implements Item {
    private ZonedDateTime start;
    private ZonedDateTime end;
    private int id;

    public Semester(ZonedDateTime start, ZonedDateTime end) {
        this.start = start;
        this.end = end;
    }

    public ZonedDateTime getStart() {
        return start;
    }

    public void setStart(ZonedDateTime start) {
        this.start = start;
    }

    public ZonedDateTime getEnd() {
        return end;
    }

    public void setEnd(ZonedDateTime end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "Semester{" +
                "start=" + start +
                ", end=" + end +
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
