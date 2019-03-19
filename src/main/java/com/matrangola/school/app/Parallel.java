package com.matrangola.school.app;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Parallel {
    public static void main(String[]  args) {
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
}
