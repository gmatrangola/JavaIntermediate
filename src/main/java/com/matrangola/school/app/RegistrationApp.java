package com.matrangola.school.app;

import com.matrangola.school.dao.inmemory.InMemoryCourseDAO;
import com.matrangola.school.dao.inmemory.InMemorySectionDAO;
import com.matrangola.school.dao.inmemory.InMemorySemesterDAO;
import com.matrangola.school.domain.Course;
import com.matrangola.school.domain.Section;
import com.matrangola.school.domain.Semester;
import com.matrangola.school.domain.Student;
import com.matrangola.school.service.CourseService;
import com.matrangola.school.service.ScheduleService;
import com.matrangola.school.service.StudentService;

import java.time.DayOfWeek;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class RegistrationApp {

	int value;
	private static StudentService studentService;
	private static CourseService courseService;
	private static ScheduleService scheduleService;

	private static Executor executor;
	private static ExecutorCompletionService<String> completionService;

	public static void main(String[] args) {
		executor = Executors.newFixedThreadPool(10, r -> {
			Thread thread = new Thread(r);
			thread.setName("Formatter");
			thread.setDaemon(true);
			return thread;
		});
		completionService = new ExecutorCompletionService<>(executor);

		primeAndPrintBoth();
		 //postRequestToAddAStudent();
		 //getRequestForAllStudents();
		printFullTime();
	}

	private static void printFullTime() {
		List<Student> fullTime = studentService.getFullTimeStudents();
		System.out.println("Full Time Students: " +  Arrays.toString(fullTime.toArray()));
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
		studentService = new StudentService();
		init(studentService);
		List<Student> students = studentService.getAllStudents();
		students.forEach(System.out::println);

		courseService = new CourseService(new InMemoryCourseDAO());
		init(courseService);
		List<Course> courses = courseService.getAllCourses();
		courses.forEach(System.out::println);

		scheduleService = new ScheduleService(new InMemorySemesterDAO(), new InMemorySectionDAO());
		ZonedDateTime start = ZonedDateTime.of(2020, 9, 1, 0, 0, 0, 0, ZoneId.of("America/New_York"));
		ZonedDateTime end = ZonedDateTime.of(2020, 12, 23, 0, 0, 0, 0, ZoneId.of("America/New_York"));

		Semester fall = new Semester(start, end);
		scheduleService.addSemester(fall);
        Course course = courseService.getAllCourses().get(0);
		scheduleService.schedule(fall, course, "Dr Smith", students, DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY);
        scheduleService.schedule(fall, courses.get(1), "Dr Jones", students, DayOfWeek.TUESDAY, DayOfWeek.THURSDAY);

        scheduleService.getAllSections().forEach(s -> s.getStudents().forEach(student ->  printSectionStudent(s,student)));
        scheduleService.getAllSections().forEach(s -> {
			System.out.println("Section: " + s.getCourse().getCode());
			s.getStudents().forEach(student -> System.out.println("Student: " + student.getName()));
		});

        new Thread(() -> printAllSchedules(fall)).start();

	}

	private static void printAllSchedules(Semester fall) {
		StringBuilder sb = new StringBuilder();

		int futures = 0;
		for (Section section : scheduleService.getAllSections()) {
			completionService.submit(() -> printSchedule(fall, section));
			futures++;
		}

		int completed = 0;
		while(completed < futures) {
			try {
				Future<String> future = completionService.take();
				sb.append(future.get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			completed++;
		}
		System.out.println(sb.toString());
	}

	private static void printSectionStudent(Section section, Student student) {
		System.out.println(section.getCourse().getCode() + ": " + student.getName());
	}

	private static String printSchedule(Semester semester, Section section) {
		StringBuilder sb = new StringBuilder();
        ZonedDateTime start = semester.getStart();
        ZonedDateTime end = semester.getEnd();

        ZonedDateTime first = null;
        for (DayOfWeek day : section.getDays()) {
            ZonedDateTime next = start.with(TemporalAdjusters.nextOrSame(day));
            if (first == null || next.isBefore(first)) first = next;
        }

        ZonedDateTime meet = first;
        while ( meet.isBefore(end)) {
            for (DayOfWeek day : section.getDays()) {
                meet = meet.with(TemporalAdjusters.nextOrSame(day));
                sb.append(printMeeting(section.getCourse().getCode(), meet));
            }
        }
        return sb.toString();
    }

    private static String printMeeting(String name, ZonedDateTime next) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("E, MMMM d, yyyy");
        return name + ": " + format.format(next);
    }

    public static void init(StudentService ss) {
		ss.createStudent("Manoj", "282 939 9944", Student.Status.FULL_TIME);
		ss.createStudent("Charlene", "282 898 2145", Student.Status.FULL_TIME);
		ss.createStudent("Firoze", "228 678 8765", Student.Status.HIBERNATING);
		ss.createStudent("Joe", "3838 678 3838", Student.Status.PART_TIME);
	}

	public static void init(CourseService cs) {
		cs.createCourse("Math-101", "Intro To Math");
		cs.createCourse("Math-201", "More Math");
		cs.createCourse("Phys-101", "Baby Physics");
	}

}
