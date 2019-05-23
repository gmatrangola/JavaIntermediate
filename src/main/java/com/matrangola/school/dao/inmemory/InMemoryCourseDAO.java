package com.matrangola.school.dao.inmemory;

import com.matrangola.school.domain.Course;

import java.util.Map;

public class InMemoryCourseDAO extends InMemoryDAO {

	public Map<Integer, Course> getCourses() {
		return items;
	}

	public void setCourses(Map<Integer, Course> courses) {
		this.items = courses;
	}
}
