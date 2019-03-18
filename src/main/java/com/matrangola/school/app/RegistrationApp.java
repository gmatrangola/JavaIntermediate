package com.matrangola.school.app;

import com.matrangola.school.domain.Course;
import com.matrangola.school.domain.Student;
import com.matrangola.school.service.CourseService;
import com.matrangola.school.service.ScheduleService;
import com.matrangola.school.service.StudentService;

import static java.time.DayOfWeek.*;
import java.util.Comparator;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Stream;

public class RegistrationApp {

	int value;

	public static void main(String[] args) {
		primeAndPrintBoth();
		 //postRequestToAddAStudent();
		 //getRequestForAllStudents();
	}


	public static void postRequestToAddAStudent() {
		StudentService ss = new StudentService();
		ss.createStudent("New One", "282 484 9944", Student.Status.FULL_TIME);

		List<Student> students = ss.getAllStudents();
		students.forEach(System.out::println);
	}

	public static void getRequestForAllStudents() {
		StudentService ss = new StudentService();
		List<Student> students = ss.getAllStudents();
		System.out.println("All Students: " + students.size());
		students.forEach(System.out::println);
	}

	public static void primeAndPrintBoth() {
		StudentService ss = new StudentService();
		init(ss);
		List<Student> students = ss.getAllStudents();

		students.stream().sorted(Comparator.comparing(Student::getName)).forEach(RegistrationApp::niceStudent);



		CourseService cs = new CourseService();
		init(cs);
		List<Course> courses = cs.getAllCourses();
		courses.forEach(System.out::println);

		Stream<Course> courseStream = cs.getAllCourses().stream();
		Float averageCredits = courseStream
				.map(Course::getCredits)
				.peek( aFloat -> System.out.println("f = " + aFloat))
				.reduce(0.0f, (a, b) -> a + b);
		System.out.println("Average Credits " + averageCredits / courses.size());

		courseStream = cs.getAllCourses().stream();
		System.out.println("Lower level classes...");
		courseStream.filter(course -> course.getCredits() < 3.0f).forEach(System.out::println);

		OptionalDouble easyAverage = cs.getAllCourses().stream().mapToDouble(Course::getCredits).average();

		ScheduleService scheduleService = new ScheduleService();
		scheduleService.schedule(cs.getCourse(0), MONDAY, WEDNESDAY, FRIDAY);
		scheduleService.schedule(cs.getCourse(0), TUESDAY, THURSDAY);
		scheduleService.schedule(cs.getCourse(1), THURSDAY, TUESDAY);
		scheduleService.schedule(cs.getCourse(2), MONDAY, WEDNESDAY, FRIDAY);
		scheduleService.schedule(cs.getCourse(2), TUESDAY, THURSDAY);



	}

	private static void niceStudent(Student student) {
		 System.out.println("Student: " + student.getName());
	}

	public static void init(StudentService ss) {
		ss.createStudent("Manoj", "282 939 9944", Student.Status.FULL_TIME);
		ss.createStudent("Charlene", "282 898 2145", Student.Status.FULL_TIME);
		ss.createStudent("Firoze", "228 678 8765", Student.Status.HIBERNATING);
		ss.createStudent("Joe", "3838 678 3838", Student.Status.PART_TIME);
	}

	public static void init(CourseService cs) {
		cs.createCourse("Math-101", "Intro To Math", 1.0f);
		cs.createCourse("Math-201", "More Math", 2.5f);
		cs.createCourse("Phys-101", "Baby Physics", 3.0f);
	}

}
