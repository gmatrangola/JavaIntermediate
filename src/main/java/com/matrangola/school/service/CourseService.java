package com.matrangola.school.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matrangola.school.dao.BaseDAO;
import com.matrangola.school.dao.inmemory.InMemoryCourseDAO;
import com.matrangola.school.domain.Course;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class CourseService {

	private BaseDAO<Course> courseDAO;
	
	public CourseService(BaseDAO<Course> dao)
	{
		courseDAO = dao;
	}
	
	public Course createCourse(String code, String title) {
		Course course = new Course(code, title);
		course = courseDAO.create(course);
		
		return course;
	}
	
	public Course createCourse(Course course) {
		course = courseDAO.create(course);
		
		return course;
	}

	public Course createCourse(String code, String title, float credits) {
		Course c = createCourse(code, title);
		if (c == null) return null;
		c.setCredits(credits);
		return c;
	}


	public void deleteCourse(int id) {
		Course course = courseDAO.get(id);
		if(course != null) {
			courseDAO.delete(course);
		}
	}
	
	public void updateCourse(Course course) {
		courseDAO.update(course);
	}
	
	public Course getCourseByCode(String code) {
		List<Course> courses = courseDAO.getAll();
		for(Course course : courses) {
			if(course.getCode().equals(code)) {
				return course;
			}
		}
		return null;
	}
	
	public Course getCourse(int id) {
		return courseDAO.get(id);
	}
	
	public List<Course> getAllCourses() {
		return courseDAO.getAll();
	}
	
	public BaseDAO<Course> getCourseDAO() {
		return courseDAO;
	}

	public void setCourseDAO(BaseDAO<Course> courseDAO) {
		this.courseDAO = courseDAO;
	}

	public void persist(File jsonDir) throws IOException {
		for (Course course : getAllCourses()) {
			persist(jsonDir, course);
		}
	}

	private void persist(File jsonDir, Course course) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.writeValue(new File(jsonDir, "Course-" + course.getCode() + ".json"), course);
	}

	public void load(File jsonDir) throws IOException {
		File[] files = jsonDir.listFiles((dir, name) -> name.startsWith("Course"));
		for (File file : files) {
			loadJson(file);
		}

	}

	private void loadJson(File file) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		Course course = objectMapper.readValue(file, Course.class);
		courseDAO.create(course);
	}

}
