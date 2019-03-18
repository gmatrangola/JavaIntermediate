package com.matrangola.school.dao.inmemory;

import com.matrangola.school.domain.Course;

import java.util.HashMap;
import java.util.Map;

public class InMemoryCourseDAO extends RegisterDAO<Course> {

	public void deleteStore() {
		itemMap = null;
	}
	
	public void createStore() {
		itemMap = new HashMap<Integer, Course>();
	}
	
	public Map<Integer, Course> getCourses() {
		return itemMap;
	}

	public void setCourses(Map<Integer, Course> courses) {
		this.itemMap = courses;
	}
}
