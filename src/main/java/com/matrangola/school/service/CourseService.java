package com.matrangola.school.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matrangola.school.dao.BaseDAO;
import com.matrangola.school.dao.DaoException;
import com.matrangola.school.dao.inmemory.InMemoryCourseDAO;
import com.matrangola.school.domain.Course;
import com.matrangola.school.domain.Internship;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class CourseService {

	private BaseDAO<Course> courseDAO;
	
	public CourseService(File jsonDir) {
		courseDAO = new InMemoryCourseDAO();

		ScheduledExecutorService executor = Executors.newScheduledThreadPool(10, r -> {
			System.out.println("Thread Factory: ");
			Thread thread = new Thread(r, "Course Loader");
			return thread;
		});

		ScheduledFuture<?> result = executor.scheduleWithFixedDelay(() -> {
			try {
				load(jsonDir);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (DaoException e) {
				e.printStackTrace();
			}
			long count = getAllCourses().stream().count();
			long tid = Thread.currentThread().getId();
			String name = Thread.currentThread().getName();
			System.out.println("Loaded " + count + " object. Thread: " + name + ": " + tid);
		}, 5, 10, TimeUnit.SECONDS);
	}
	
	public Course createCourse(String code, String title, float credits) {
		Course course = new Course(code, title);
		course.setCredits(credits);
		course = courseDAO.create(course);
		
		return course;
	}

	public Internship createInternship(String code, String title, float credits) {
		Internship internship = new Internship(code, title);
		internship = (Internship) courseDAO.create(internship);
		return internship;
	}
	
	public Course createCourse(Course course) {
		course = courseDAO.create(course);
		
		return course;
	}
	
	public void deleteCourse(int id) {
		Course course = courseDAO.get(id);
		if(course != null) {
			courseDAO.delete(course);
		}
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

	public void load(File jsonDir) throws IOException, DaoException {
		File[] files = jsonDir.listFiles((dir, name) -> name.startsWith("Course"));
		for (File file : files) {
			loadJson(file);
		}

	}

	private void loadJson(File file) throws IOException, DaoException {
		ObjectMapper objectMapper = new ObjectMapper();
		Course course = objectMapper.readValue(file, Course.class);
		courseDAO.load(course);
	}
}
