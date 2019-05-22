package com.matrangola.demos;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class StreamDemo {

    private void foo() throws IOException {
        LongStream ls = LongStream.rangeClosed(1, 20);
//        ls.forEach( p -> System.out.println("i = " + p));
        long sum = ls.reduce(0, (a,b) -> a+b);
        System.out.println("sum = " + sum);

        Stream<String> dogStream = Stream.of("Pippa", "Natty", "Oscar");
        Set<String> dogs = dogStream.collect(Collectors.toSet());

        Stream<String> built = Stream.<String>builder().add("Pippa").add("Natty").add("oscar").add("Piper").build();
        boolean found = built.anyMatch(p -> p.startsWith("P"));
        System.out.println("from anyMatch = " + found);

        Stream<String> built2 = Stream.<String>builder().add("Pippa").add("Natty").add("oscar").add("Piper").build();
        boolean found2 = built2.allMatch(p -> p.length() == 5);
        System.out.println("from allMatch = " + found2);

        Stream<String> kennel = Stream.<String>builder().add("Pippa").add("Natty").add("oscar").add("Piper").build();
        long count = kennel.filter(p -> p.startsWith("P")).count();
        System.out.println("count P " + count);

        Stream<String> kennel2 = Stream.<String>builder().add("Pippa").add("Natty").add("oscar").add("Piper").build();
        Stream<String> m = kennel2.map(s -> s.toUpperCase());
        Set<String> upperCase = m.collect(Collectors.toSet());
        upperCase.forEach(System.out::println);

        File file = new File("Sherlock.txt");
        long uniqueWords = java.nio.file.Files
                .lines(Paths.get(file.toURI()), Charset.defaultCharset())
                .peek(s -> System.out.println("line = " + s))
                .flatMap(line -> Arrays.stream(line.split(" .")))
                .peek(w -> System.out.println("word = " + w))
                .distinct()
                .count();
        System.out.println("unique = " + uniqueWords);

        Stream<String> kennel3 = Stream.<String>builder().add("Pippa").add("natty").add("Oscar").add("Piper").build();
        List<String> sorted = kennel3
                .sorted(Comparator.comparing(String::toUpperCase))
                .collect(Collectors.toList());
        sorted.forEach(System.out::println);

    }

    public static void main(String[] args) throws IOException {
        StreamDemo sd = new StreamDemo();
        sd.foo();
    }

}
