package com.matrangola.demos;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingByConcurrent;

public class ParallelDemo {
    public static final String[] CLASSICS = {"TI/99-4a", "Apple", "Apple II", "Apple //c", "PCjr"};

    public static void parallel() {
        Stream<String> parallelStream = Arrays.asList(CLASSICS).parallelStream();

        List<String> pcs = parallelStream
                .map(String::toUpperCase)
                .peek(s -> System.out.println("map operation " + Thread.currentThread().getId() + ": " + s))
                .sorted()
                .collect(Collectors.toList());

        for (String pc : pcs) {
            System.out.println(pc);
        }
    }

    public static void wordCounter() throws IOException {
        Path path = Paths.get("sherlock.txt");

        long start = System.currentTimeMillis();
        Map<String, Long> wordCount = Files.lines(path).parallel()
                .flatMap(line -> Arrays.stream(line.trim().split("\\s")))
                .map(word -> word.replaceAll("[^a-zA-Z]", "").toLowerCase().trim())
                .filter(word -> word.length() > 0)
                .map(word -> new AbstractMap.SimpleEntry<>(word, 1))
                .collect(groupingByConcurrent(AbstractMap.SimpleEntry::getKey, counting()));
        System.out.println("Time = " + (System.currentTimeMillis() - start));
//        wordCount.forEach((k, v) -> System.out.println(String.format("%s ==>> %d", k, v)));

        System.out.println("By WORD --------------");
        List<Map.Entry<String, Long>> byWord = wordCount.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .collect(Collectors.toList());
        byWord.forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));

        System.out.println("By COUNT --------------");
        List<Map.Entry<String, Long>> byCount = wordCount.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getValue))
                .collect(Collectors.toList());
        byCount.forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));
    }

    public static void main(String[] args) throws IOException {
        wordCounter();
    }

}
