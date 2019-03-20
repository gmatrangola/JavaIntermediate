package com.matrangola.school.app;

import com.matrangola.school.dao.DaoException;
import com.matrangola.school.domain.*;
import com.matrangola.school.service.CourseService;
import com.matrangola.school.service.ScheduleService;
import com.matrangola.school.service.StudentService;

import static java.time.DayOfWeek.*;

import java.io.File;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Comparator;
import java.util.List;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.stream.Stream;

public class RegistrationApp {

	private final File jsonDir;
	int value;
	private final StudentService studentService;
	private final CourseService courseService;
	private final ScheduleService scheduleService;
	private Section sectionW;

	public RegistrationApp(File file) {
		jsonDir = file;
		jsonDir.mkdir();
		studentService = new StudentService();
		courseService = new CourseService(jsonDir);
		scheduleService = new ScheduleService();
	}

	public static void main(String[] args) {
		if(args.length < 2) {
			System.out.println("Usage: registration in|out directory");
		}
		else {
			RegistrationApp app = new RegistrationApp(new File(args[1]));
			if (args[0].equals("out")) {
				app.primeServices();
				app.printScheduleData();
			}
			else if (args[0].equals("in")) {
				app.load();
				app.printCourseData();
			}
		}

		 //postRequestToAddAStudent();
		 //getRequestForAllStudents();
	}

	private void load() {
		try {
			courseService.load(jsonDir);
		} catch (IOException e) {
			System.err.println("Error reading JSON file "+ e.getLocalizedMessage());
			e.printStackTrace();
		} catch (DaoException e) {
			System.err.println("Error inserting JSON file "+ e.getLocalizedMessage());
			e.printStackTrace();
		}
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

	public void printScheduleData() {
		List<Section> sections = scheduleService.getSections();
		for (Section section : sections) {
			Course course1 = section.getCourse();
			System.out.println("Course " + course1.getTitle() + " Days: " + printDays(section));
 		}

		printSchedule(scheduleService, "Smith", "Jones", "Washington");
        if (sectionW != null) printSchedule(sectionW);

		try {
			courseService.persist(jsonDir);
		} catch (IOException e) {
			System.err.println("Error saving Courses: " + e.getLocalizedMessage());
			e.printStackTrace();
		}

//		System.out.println("Section IDs");
//		printIds(scheduleService.getSections(), cs.getAllCourses(), ss.getAllStudents());
	}

	private void printCourseData() {
		List<Student> students = studentService.getAllStudents();

		students.stream().sorted(Comparator.comparing(Student::getName)).forEach(RegistrationApp::niceStudent);

		List<Course> courses = courseService.getAllCourses();
		courses.forEach(System.out::println);

		Stream<Course> courseStream = courseService.getAllCourses().stream();
		Float totalCredits = courseStream
				.map(Course::getCredits)
				.peek( aFloat -> System.out.println("f = " + aFloat))
				.reduce(0.0f, (a, b) -> a + b);
		System.out.println("Average Credits " + totalCredits / courses.size());

		courseStream = courseService.getAllCourses().stream();
		System.out.println("Lower level classes...");
		courseStream.filter(course -> course.getCredits() < 3.0f).forEach(System.out::println);

		OptionalDouble easyAverage = courseService.getAllCourses().stream().mapToDouble(Course::getCredits).average();
		if (easyAverage.isPresent()) System.out.println("Easy Average: " + easyAverage.getAsDouble());
	}

	private void primeServices() {
		init(studentService);
		init(courseService);
		initSchedule();
	}

	private void initSchedule() {
		Course course = courseService.getCourse(1);

		ZonedDateTime start = ZonedDateTime.of(2020, 9, 1, 0, 0, 0, 0, ZoneId.of("America/New_York"));
		ZonedDateTime end = ZonedDateTime.of(2020, 12, 23, 0, 0, 0, 0, ZoneId.of("America/New_York"));

		Period length = Period.between(start.toLocalDate(), end.toLocalDate());
		System.out.println("Semester is " + length.getDays() + " days long.");

		Semester semester = new Semester(start, end);

		scheduleService.schedule(semester, course, "Smith", MONDAY, WEDNESDAY, FRIDAY);
		scheduleService.schedule(semester, courseService.getCourse(1), "Jones", TUESDAY, THURSDAY);
		scheduleService.schedule(semester, courseService.getCourse(2), "Smith", THURSDAY, TUESDAY);
		sectionW = scheduleService.schedule(semester, courseService.getCourse(3), "Washington", MONDAY, WEDNESDAY, FRIDAY);
		scheduleService.schedule(semester, courseService.getCourse(3), "Washington", TUESDAY, THURSDAY);
	}

	public static void printSchedule(Section section) {
        Semester semester = section.getSemester();
        ZonedDateTime start = semester.getStart();
        ZonedDateTime end = semester.getEnd();

        ZonedDateTime first = null;
        for (DayOfWeek day : section.getDaysOfWeek()) {
            ZonedDateTime next = start.with(TemporalAdjusters.nextOrSame(day));
            if (first == null || next.isBefore(first)) first = next;
        }

        ZonedDateTime meet = first;
        while ( meet.isBefore(end)) {
            for (DayOfWeek day : section.getDaysOfWeek()) {
                meet = meet.with(TemporalAdjusters.nextOrSame(day));
                printMeeting(meet);
            }
        }
    }

    private static void printMeeting(ZonedDateTime next) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("EE MMMM d, yyyy");
        System.out.println(format.format(next));
    }

	private static void printSchedule(ScheduleService scheduleService, final String ... instructors) {
		for (String instructor : instructors) {
			System.out.println(instructor + " Course Codes:");
			Set<String> courses = scheduleService.getCourseCodesByInstructor(instructor);
			courses.forEach(System.out::println);
			List<Section> sections = scheduleService.getSectionsByInstructor(instructor);
			System.out.println(instructor + " Sections:");
			sections.forEach(System.out::println);
		}

	}

	public static void printIds(List<? extends RegistrationItem> ... items) {
		for (List<? extends RegistrationItem> item : items) {
			item.stream().mapToInt(RegistrationItem::getId).forEach(System.out::println);
		}
	}

	private static String printDays(Section section) {
		StringBuilder sb = new StringBuilder();
		for (DayOfWeek dayOfWeek : section.getDaysOfWeek()) {
			if (sb.length() > 0) sb.append(", ");
			sb.append(dayOfWeek);
		}
		return sb.toString();
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
