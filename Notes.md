# Intermediate Java

## Collections

<https://docs.oracle.com/javase/8/docs/api/java/util/Collection.html>



HashSet - Iterator

```java
String[] classics = {"TI/99-4a", "Apple", "Apple II", "Apple //c", "PCjr"};

// Mention Generics
Set<String> hashSet = new HashSet();
for (String classic : classics) {
    hashSet.add(classic);
}

if (hashSet.contains("Apple")) System.out.println("Has Apple");
if (hashSet.contains("Commodore 64")) System.out.println("Has C64");

Iterator it = hashSet.iterator();
while(it.hasNext()) {
    System.out.println("it = " + it.next());
}

for (String s : hashSet) {
    System.out.println("s = " + s);
}

hashSet.forEach(System.out::println);
```



LinkedList - adds random access

```java
String[] classics = {"TI/99-4a", "Apple", "Apple II", "Apple //c", "PCjr"};

LinkedList<String> linkedList = new LinkedList<>();
for (String classic : classics) {
   linkedList.add(classic);
}

System.out.println("first = " + linkedList.getFirst());
System.out.println("last = " + linkedList.getLast());

System.out.println("rando = " + linkedList.get(3));

Iterator<String> it = linkedList.iterator();
while (it.hasNext()) {
    System.out.println("it is " + it.next());
}

for (String s : linkedList) {
    System.out.println("s = " + s);
}

linkedList.forEach(System.out::println);
```

ArrayList - Better at random index than LinkedList

```java
String[] dogs = {"Pippa", "Cleo", "Natty", "Fox", "Winston"};

ArrayList<String> arrayList = new ArrayList<>();
for (String dog : dogs) {
    arrayList.add(dog);
}
```

HashMap

```java
public class MainMap {

    public static class Computer {
        String name;
        int ram;

        public Computer(String name, int k) {
            this.name = name;
            ram = k;
        }
    }

    public static void main(String[] args) {
        Map<String, Computer> computerMap = new HashMap<>();

        addComputer(computerMap, "TI/99-4a", 8);
        addComputer(computerMap, "Apple", 8);
        addComputer(computerMap, "Apple II", 16);
        addComputer(computerMap, "Apple //c", 128);
        addComputer(computerMap, "PCjr", 256);

        Computer pcJr = computerMap.get("PCjr");
        System.out.println("Memory for PCjr = " + pcJr.ram + "k");

        for (Map.Entry<String, Computer> entry : computerMap.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue().ram + "k");
        }

    }

    private static void addComputer(Map<String, Computer> computerMap, String name, int k) {
        computerMap.put(name, new Computer(name, k));
    }
}
```

TreeMap

<https://docs.oracle.com/javase/8/docs/api/java/util/TreeMap.html>

Like HashMap **But Sorted by key :)**

```java
TreeMap<String, Computer> treeMap = new TreeMap<>();
buildMap(treeMap);
String more  = treeMap.higherKey("Apple II");
System.out.println("more = " + more);
System.out.println("Sorted");
for (String s : treeMap.keySet()) {
    System.out.println(s);
}
```

Queue

<https://docs.oracle.com/javase/8/docs/api/java/util/Queue.html>

```java
String[] classics = {"TI/99-4a", "Apple", "Apple II", "Apple //c", "PCjr"};
Queue<String> queue = new LinkedList<>();
for (String classic : classics) {
    queue.offer(classic);
}

while( queue.size() > 0 ) {
    System.out.println(queue.remove());
}
```

PriorityQueue

```java
PriorityQueue<String> priorityQueue = new PriorityQueue<>();

for (String classic : classics) {
    priorityQueue.offer(classic);
}

System.out.println("Priority Queue:");

while( priorityQueue.size() > 0 ) {
    System.out.println(priorityQueue.remove());
}
```

## Streams

<https://docs.oracle.com/javase/8/docs/api/java/util/stream/package-summary.html>



### Terminal

forEach()

```java
longStream.forEach(
        p -> System.out.println("i=" + p)
);
```

reduce()

```java
long sum = longStream.reduce(0, (a,b) -> a+b);
System.out.println("sum = " + sum);
```

collect() - use collectors to convert to another container

```java
Stream<String> dogStream = Stream.of("Pippa", "Natty", "Oscar");
Set<String> dogs = dogStream.collect(Collectors.toSet());
```

anyMatch(): is it found anywhere?

```java
boolean found = dogStream.anyMatch(p -> p.startsWith("P"));
System.out.println("found = " + found);
```

allMatch(): to all elements match the predicate

```java
boolean all = dogStream.allMatch(p -> p.length() == 5);
```

### Intermediate

filter

```java
long count = dogStream.filter(p -> p.startsWith("P")).count();
```

map - using collect for terminal

```java
List<String> upper = dogStream.map(s -> s.toUpperCase()).collect(Collectors.toList())
```

flatmap

```java
File file = new File(sourceFileURI);

long uniqueWords = java.nio.file.Files
        .lines(Paths.get(file.toURI()), Charset.defaultCharset())
        .flatMap(line -> Arrays.stream(line.split(" ."))).distinct()
        .count();
```



peak - good for debugging

```java
List<String> upper = dogStream
        .peek(s -> System.out.println("pre: " + s))
        .map(String::toUpperCase)
        .peek(s -> System.out.println("after " + s))
        .collect(Collectors.toList());
```

sorted

```java
List<String> sorted = dogStream.sorted().collect(Collectors.toList());
for (String s : sorted) {
    System.out.println(s);
}
```

## Parallel Stream

```java
List<String> classics = Arrays.asList("TI/99-4a", "Apple", "Apple II", "Apple //c", "PCjr");
Stream<String> parallel = classics.parallelStream();
List<String> old = parallel.map(s -> s.toUpperCase()).collect(Collectors.toList());
for (String s : old) {
    System.out.println(s);
}
```



Threading

```java
List<String> classics = Arrays.asList("TI/99-4a", "Apple", "Apple II", "Apple //c", "PCjr");
Stream<String> parallel = classics.parallelStream();
List<String> old = parallel
        .map(s -> s.toUpperCase()).peek(s -> {
            System.out.println(Thread.currentThread().getId() + " - " + s);
        })
        .sorted()
        .peek(s -> {
            System.out.println(Thread.currentThread().getId() + " - " + s);
        })
        .collect(Collectors.toList());
for (String s : old) {
    System.out.println(s);
}
```

### Processing Text File

```java
Path path = Paths.get("data/Sherlock.txt");
Map<String, Long> wordCount = Files.lines(path).parallel()
        .flatMap(line -> Arrays.stream(line.trim().split("\\s")))
        .map(word -> word.replaceAll("[^a-zA-Z]", "").toLowerCase().trim())
        .filter(word -> word.length() > 0)
        .map(word -> new AbstractMap.SimpleEntry<>(word, 1))
        .collect(groupingByConcurrent(AbstractMap.SimpleEntry::getKey, counting()));

wordCount.forEach((k, v) -> System.out.println(String.format("%s ==>> %d", k, v)));
```



## Generics

```java
public static <T> void printList(String title, Iterable<T> thingy) {
    System.out.println(title);
    for (T o : thingy) {
        System.out.println(o.toString());
    }
}

public static <T, V extends Iterable<T>> void printList2(String title, V items) {
    System.out.println(title);
    for (T item : items) {
        System.out.println(item);
    }
}
```

```java
public static int findMaxId(List<? extends IdBean> items) {
    return items.stream().mapToInt(IdBean::getId).max().getAsInt();
}
```

## Date Time API

```java
public static void printSchedule(Section section) {
    Semester semester = section.getSemester();
    ZonedDateTime start = semester.getStart();
    ZonedDateTime end = semester.getEnd();

    ZonedDateTime first = null;
    for (DayOfWeek day : section.getDays()) {
        ZonedDateTime next = start.with(TemporalAdjusters.nextOrSame(day));
        if (first == null || next.isBefore(first)) first = next;
    }

    ZonedDateTime meet = first;
    while (first.isBefore(end)) {
        for (DayOfWeek day : section.getDays()) {
            ZonedDateTime next = start.with(TemporalAdjusters.nextOrSame(day));
            printMeeting(next);
        }

    }
}

private static void printMeeting(ZonedDateTime next) {
    DateTimeFormatter format = DateTimeFormatter.ofPattern("MMMM d, yyyy");
    System.out.println(format.format(next));
}
```

## Persistence with JSON

Jackson library

build.gradle file

```groovy
dependencies {
    implementation group: 'com.fasterxml.jackson.core', name: 'jackson-core', 
        version: '2.9.8'
    implementation group: 'com.fasterxml.jackson.core', name: 'jackson-databind', 
        version: '2.9.8'
    implementation group: 'com.fasterxml.jackson.core', name: 'jackson-annotations',
        version: '2.9.8'

    testCompile group: 'junit', name: 'junit', version: '4.12'
}
```

### Save

CourseService.java

```java
public void persist() throws IOException {
   for (Course course : getAllCourses()) {
      persistCourse(course);
   }

}

public void persistCourse(Course course) throws IOException {
   ObjectMapper objectMapper = new ObjectMapper();
   objectMapper.writeValue(new File(RegistrationApp.JSON_DIR, "Course-" +
                                    course.getId() + ".json"), course);

}
```

course-1.json

```json
{"id":1,"title":"Intro To Math","code":"Math-101","credits":1.0,"name":null}
```

Course.java

```java
@JsonIgnore
public float[] getCreditList() {
   return creditList;
}
```

### Load

CourseService.java

```java
public void load() throws IOException {
   File[] files = RegistrationApp.JSON_DIR.listFiles((dir, name) -> name.startsWith("Course-"));
   for (File file : files) {
      load(file);
   }

}

private void load(File file) throws IOException {
   ObjectMapper objectMapper = new ObjectMapper();
   Course course = objectMapper.readValue(file, Course.class);
   courseDAO.update(course);
}
```

RegisterDAO.java

```java
@Override
public void update(T updateObject) {
    itemMap.put(updateObject.getId(), updateObject);
    OptionalInt max = itemMap.values().stream().mapToInt(value -> value.getId()).max();
    nextId = max.getAsInt() + 1;
}
```

RegistrationApp.java

Make OO

```java
private StudentService studentService;
private CourseService courseService;
private ScheduleService scheduleService;

public RegistrationApp() {
		studentService = new StudentService();
		courseService = new CourseService();
		scheduleService = new ScheduleService();

	}

public static void main(String[] args) throws IOException {
		RegistrationApp app = new RegistrationApp();
		if (args.length == 0) {
			app.primeAndPrintBoth();
			app.courseService.persist();
		} else if (args[0].equals("load")) {
			app.courseService.load();
			app.printCourses();
		}

		//postRequestToAddAStudent();
		 //getRequestForAllStudents();
	}
```

```java
private void printCourses() throws IOException {
   List<Course> courses = courseService.getAllCourses();
   courses.forEach(System.out::println);

   courseService.persist();

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
```



## Threading with Executors

CourseService

Use Thread() first then refactor

Periodic polling

```java
public void poll() {
   ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

   ScheduledFuture<?> result = executor.scheduleAtFixedRate(() -> {
      try {
         load();
      } catch (IOException e) {
         System.err.println("Error loading dir");
         e.printStackTrace();
      }
   }, 5, 1, TimeUnit.SECONDS);

   if (result.isCancelled()) System.out.println("canceled");
}
```

## Junit and Mokito

CourseServiceTest.java

```java
public class CourseServiceTest {

    @Mock
    InMemoryCourseDAO daoMock;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void createCourse() {
        // InMemoryCourseDAO testDao = mock(InMemoryCourseDAO.class);
        Course testCourse = new Course("MATH101", "Intro to Math");
        when(daoMock.create(testCourse)).thenReturn(testCourse);
        when(daoMock.get(1)).thenReturn(testCourse);

        CourseService cs = new CourseService(daoMock);
        Course course = cs.createCourse("MATH101", "Intro to Math", 3.0f);

        assertEquals("MATH101", course.getCode());

    }

    @Test
    public void getCourseByCode() {
        Course testCourse = new Course("MATH101", "Intro to Math");
        List<Course> testList = Arrays.asList(testCourse);
        when(daoMock.getAll()).thenReturn(testList);
        CourseService cs = new CourseService(daoMock);

        Course course = cs.getCourseByCode("MATH101");
        assertEquals("MATH101", course.getCode());

        Course course2 = cs.getCourseByCode("MATH2");
        assertNull(course2);

    }
```

# Demo Screencasts on YouTube

https://youtu.be/7eloohs4Zes

https://youtu.be/5Kqk5eRcK1I

https://youtu.be/7esn4p3wuYY