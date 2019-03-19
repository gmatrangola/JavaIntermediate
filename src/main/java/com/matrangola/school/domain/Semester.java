package com.matrangola.school.domain;

import java.time.ZonedDateTime;

public class Semester {
    private final ZonedDateTime start;
    private final ZonedDateTime end;

    public Semester(ZonedDateTime start, ZonedDateTime end) {
        this.start = start;
        this.end = end;
    }

    public ZonedDateTime getStart() {
        return start;
    }

    public ZonedDateTime getEnd() {
        return end;
    }
}
