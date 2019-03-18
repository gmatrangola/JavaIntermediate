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

