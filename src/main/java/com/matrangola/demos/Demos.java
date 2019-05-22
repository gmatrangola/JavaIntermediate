package com.matrangola.demos;

import java.util.*;

import static java.lang.System.exit;


public class Demos {

    public static final String[] CLASSICS = {"TI/99-4a", "Apple", "Apple II", "Apple //c", "PCjr"};

    protected String[] stuff = new String[4];

    Set<String> foo = new HashSet<>();

    public Demos() {
        stuff[0] = "TI/99-4a";
        stuff[1] = CLASSICS[2];
        stuff[2] = CLASSICS[3];
    }

    void addStuff() {
        for (String pc : CLASSICS) {
            foo.add(pc);
        }

        if (foo.contains("Apple")) System.out.println("Has Apple");
        if (foo.contains("Amega")) System.out.println("Has Amega");

        Iterator it = foo.iterator();
        while(it.hasNext()) {
            System.out.println("it = " + it.next());
        }

        for (String classic : foo) {
            System.out.println("classic = " + classic);
        }

        LinkedList<String> linkedList = new LinkedList<>();
        for (String classic : CLASSICS) {
            linkedList.add(classic);
        }

        for (String s : linkedList) {
            System.out.println("List " + s);
        }

        List<String> arrayList = new ArrayList<>(Arrays.asList(CLASSICS));
        System.out.println("All");
        System.out.println("Array = " + Arrays.toString(arrayList.toArray()));
        arrayList.forEach(System.out::println);

        List<String> copy = Filter.filterListCopy(arrayList, s -> s.length() > 5);
        System.out.println("\n\nFiltered > 5");
        copy.forEach(System.out::println);

        Filter.Test<String> myLambda = s -> {
            boolean include = s.length() > 5;
            include = include && s.startsWith("A");
            return include;
        };
        List<String> complex = Filter.filterListCopy(arrayList, myLambda);

        System.out.println("\nComplex");
        complex.forEach(System.out::println);

        Filter.filterList(arrayList, s -> s.startsWith("T"));
        System.out.println("\n\nFiltered T");
        arrayList.forEach(System.out::println);

        Filter.prettyPrint(copy, () -> {
            System.out.println("something");
            System.out.println("Something else");
        });

    }

    public static void main(String[] args) {
        Demos demos = new Demos();

        demos.addStuff();
        exit(1);
    }
}
