package com.matrangola.school.dao.inmemory;

import com.matrangola.school.domain.Student;

import java.util.HashMap;
import java.util.Map;

public class InMemoryStudentDAO extends RegisterDAO<Student> {

	public void deleteStore() {
		itemMap = null;
	}

	public void createStore() {
		itemMap = new HashMap<Integer, Student>();
	}

	public Map<Integer, Student> getStudents() {
		return itemMap;
	}
}
