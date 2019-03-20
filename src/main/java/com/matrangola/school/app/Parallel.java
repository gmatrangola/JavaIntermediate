package com.matrangola.school.app;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class Parallel {
    public static void main(String[]  args) throws IOException {
        // classics();
        wordCount();
    }

    private static void classics() {
        List<String> classics = Arrays.asList("TI/99-4a", "Apple", "Apple II", "Apple //c", "PCjr");
        Stream<String> parallel = classics.parallelStream();
//        List<String> old =
        parallel
        .map(s -> s.toUpperCase())
//                        .peek(s -> {
//                    System.out.println(Thread.currentThread().getId() + " - " + s);
//                })
//                .sorted()
//                .peek(s -> {
//                    System.out.println(Thread.currentThread().getId() + " - " + s);
//                })
        .forEachOrdered(System.out::println);
//                .collect(Collectors.toList());
//        for (String s : old) {
//            System.out.println(s);
//        }
    }

    // Get text form http://www.gutenberg.org

    public static void wordCount() throws IOException {

        // 270 ms

        Path path = Paths.get("books/sherlock.txt");
        long start = System.currentTimeMillis();

        Files.lines(path).parallel()
                .flatMap(line -> Arrays.stream(line.trim().split("\\s")))
                .map(word -> word.replaceAll("[^a-zA-Z]", "").toLowerCase().trim())
                .filter(word -> word.length() > 1)
                .map(word -> new AbstractMap.SimpleEntry<>(word, 1))
                .collect(groupingByConcurrent(AbstractMap.SimpleEntry::getKey, counting()))
                .forEach((k, v) -> System.out.println(String.format("%s ==>> %d", k, v)));
//        wordCount.forEach((k, v) -> System.out.println(String.format("%s ==>> %d", k, v)));
        System.out.println("Time: " + (System.currentTimeMillis() - start));
    }
}
